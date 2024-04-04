package com.ibanking.paymentservice.stripe.payment;

import com.ibanking.paymentservice.sendgrid.SendgridService;
import com.ibanking.paymentservice.stripe.StripePropConfig;
import com.ibanking.paymentservice.stripe.StripeService;
import com.ibanking.paymentservice.tuition.TuitionResDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PaymentService extends StripeService {

  private final SendgridService sendgridService;

  public PaymentService(StripePropConfig stripePropConfig, SendgridService sendgridService) {
    super(stripePropConfig);
    this.sendgridService = sendgridService;
  }

  @SneakyThrows(StripeException.class)
  public Session createPayment(TuitionResDto dto) {
    SessionCreateParams.LineItem.PriceData.ProductData productData =
        SessionCreateParams.LineItem.PriceData.ProductData.builder()
            .setName("Tuition Fee")
            .setDescription(
                "semesterYear"
                    + String.valueOf(dto.getSemesterYear())
                    + "semesterCode"
                    + dto.getSemesterCode().toString())
            .build();

    SessionCreateParams.LineItem.PriceData priceData =
        SessionCreateParams.LineItem.PriceData.builder()
            .setCurrency("VND")
            .setUnitAmount((long) dto.getCharges())
            .setProductData(productData)
            .build();

    SessionCreateParams.LineItem lineItem =
        SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(priceData).build();

    SessionCreateParams params =
        SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setCustomer("cus_Pmg0eUj9TVNRb7")
            .setSuccessUrl("http://localhost:8082/api/v1/stripe/payment")
            .setCancelUrl("http://localhost:8082/api/v1/stripe/payment")
            .setLocale(SessionCreateParams.Locale.VI)
            .addLineItem(lineItem)
            .build();

    return Session.create(params, requestOptions);
  }

  @SneakyThrows({StripeException.class, IOException.class})
  public void sendReceipt(String sessionId) {
    Session session = Session.retrieve(sessionId, requestOptions);

    String intent = session.getPaymentIntent();
    PaymentIntent paymentIntent = PaymentIntent.retrieve(intent);

    String latestChargeObject = paymentIntent.getLatestCharge();
    Charge charge = Charge.retrieve(latestChargeObject, requestOptions);
    String latestUrl = charge.getReceiptUrl();

    URL url = new URL(latestUrl);
    url.openConnection();
    InputStream inputStream = url.openStream();
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    for (int length; (length = inputStream.read(buffer)) != -1; ) {
      result.write(buffer, 0, length);
    }
    String body = result.toString("UTF-8");
    sendgridService.sendWithoutTemplate("kelvn.developer@gmail.com", body);
  }
}
