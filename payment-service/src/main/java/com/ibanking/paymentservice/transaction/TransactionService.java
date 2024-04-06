package com.ibanking.paymentservice.transaction;

import com.ibanking.paymentservice.client.user.UserResDto;
import com.ibanking.paymentservice.client.user.UserServiceClient;
import com.ibanking.paymentservice.common.BaseService;
import com.ibanking.paymentservice.exception.BadRequestException;
import com.ibanking.paymentservice.exception.ForbiddenException;
import com.ibanking.paymentservice.exception.NotFoundException;
import com.ibanking.paymentservice.sendgrid.SendgridService;
import com.ibanking.paymentservice.stripe.customer.CustomerService;
import com.ibanking.paymentservice.stripe.payment.PaymentService;
import com.ibanking.paymentservice.tuition.Tuition;
import com.ibanking.paymentservice.tuition.TuitionRepository;
import com.ibanking.paymentservice.tuition.TuitionService;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends BaseService<Transaction, TransactionRepository> {

  private final TuitionService tuitionService;
  private final TuitionRepository tuitionRepository;
  private final UserServiceClient userServiceClient;
  private final SendgridService sendgridService;
  private final OtpService otpService;
  private final CustomerService customerService;
  private final PaymentService paymentService;
  private final long otpTTL;

  public TransactionService(
      TransactionRepository repository,
      TuitionService tuitionService,
      TuitionRepository tuitionRepository,
      UserServiceClient userServiceClient,
      SendgridService sendgridService,
      CustomerService customerService,
      PaymentService paymentService,
      OtpService otpService) {
    super(repository);
    this.tuitionService = tuitionService;
    this.tuitionRepository = tuitionRepository;
    this.userServiceClient = userServiceClient;
    this.sendgridService = sendgridService;
    this.otpService = otpService;
    this.customerService = customerService;
    this.paymentService = paymentService;
    this.otpTTL = 300;
  }

  public Transaction getByIdForUpdate(UUID id) {
    Transaction transaction = repository.getById(id);
    if (Objects.isNull(transaction)) {
      throw new NotFoundException(modelClass, "id", id.toString());
    }
    return transaction;
  }

  @Transactional
  public Transaction create(String userId, TransactionReqDto dto) {
    UUID tuitionId = dto.getTuitionId();
    UserResDto user = userServiceClient.getUser(userId);
    Tuition tuition = tuitionService.getById(tuitionId, false);
    if (tuition.isPaid()) {
      throw new BadRequestException("Tuition has been paid");
    }

    Customer customer = customerService.retrieve(user.getStripeCustomer());
    if (customer.getBalance() < tuition.getCharges()) {
      throw new BadRequestException("Insufficient balances");
    }

    // Iterates through transactions with same tuitionId, userId and return if not expires
    long now = Instant.now().getEpochSecond();
    List<Transaction> transactions =
        (List<Transaction>) repository.findAllByTuitionIdAndUserId(tuitionId, userId);
    for (Transaction transaction : transactions) {
      if (transaction.getOtpExpiryTime() > now) {
        return transaction;
      }
    }

    String otpSecret = otpService.getOtpSecret();
    int otpCode = otpService.getTotpCode(otpSecret, otpTTL);
    long otpExpiryTime = Instant.now().getEpochSecond() + otpTTL;

    Transaction transaction =
        Transaction.builder()
            .tuitionId(tuitionId)
            .status(TransactionStatus.PENDING)
            .otpSecret(otpSecret)
            .otpExpiryTime(otpExpiryTime)
            .userId(userId)
            .build();
    transaction = repository.save(transaction);

    sendgridService.sendOtpVerification(user, otpCode);
    return transaction;
  }

  @Transactional
  public Transaction complete(String userId, UUID transactionId, TransactionCompleteDto dto) {
    Transaction transaction = this.getByIdForUpdate(transactionId);

    if (!transaction.getUserId().equals(userId)) {
      throw new ForbiddenException("Access denied");
    }

    Tuition tuition = tuitionService.getByIdForUpdate(transaction.getTuitionId());
    if (tuition.isPaid()) {
      throw new BadRequestException("Tuition has been paid");
    }

    UserResDto user = userServiceClient.getUser(userId);
    Customer customer = customerService.retrieve(user.getStripeCustomer());
    if (customer.getBalance() < tuition.getCharges()) {
      throw new BadRequestException("Insufficient balances");
    }

    long now = Instant.now().getEpochSecond();
    if (transaction.getOtpExpiryTime() < now) {
      //      transaction.setStatus(TransactionStatus.EXPIRED);
      //      repository.saveAndFlush(transaction);
      throw new ForbiddenException("OTP has expired");
    }

    if (!otpService.verifyTotpCode(transaction.getOtpSecret(), otpTTL, dto.getOtpCode())) {
      throw new ForbiddenException("Access denied");
    }

    tuition.setPaid(true);
    tuitionRepository.saveAndFlush(tuition);
    transaction.setStatus(TransactionStatus.COMPLETED);
    repository.saveAndFlush(transaction);

    // stripe
    PaymentIntent paymentIntent = paymentService.createPaymentIntent(customer, tuition);
    customerService.decreaseBalance(customer, (long) tuition.getCharges());
    paymentService.sendReceipt(customer, paymentIntent);

    return transaction;
  }

  public List<Transaction> getList(String userId) {
    TransactionEntityGraph entityGraph =
        TransactionEntityGraph.____().tuition().student().____.____();
    return (List<Transaction>)
        repository.findAllByUserIdAndStatus(userId, TransactionStatus.COMPLETED, entityGraph);
  }
}
