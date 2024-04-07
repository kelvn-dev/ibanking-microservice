package com.ibanking.paymentservice.stripe.payment;

import com.ibanking.paymentservice.sendgrid.SendgridService;
import com.ibanking.paymentservice.stripe.StripePropConfig;
import com.ibanking.paymentservice.stripe.StripeService;
import com.ibanking.paymentservice.tuition.Tuition;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import java.util.Objects;
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
  public PaymentIntent createPaymentIntent(Customer customer, Tuition tuition) {
    String description =
        String.format(
            "%s semester/%s - %s",
            tuition.getSemesterCode().getValue(),
            tuition.getSemesterYear(),
            tuition.getSemesterYear() + 1);

    PaymentIntentCreateParams paymentIntentCreateParams =
        PaymentIntentCreateParams.builder()
            .setDescription(description)
            .setCurrency("usd")
            .setAmount((long) tuition.getCharges() * 100)
            .setCustomer(customer.getId())
            .setPaymentMethod("pm_card_visa")
            .setConfirm(true)
            .setOffSession(true)
            .build();

    return PaymentIntent.create(paymentIntentCreateParams, requestOptions);
  }

  @SneakyThrows({StripeException.class})
  public String getReceiptUrl(PaymentIntent paymentIntent) {
    String latestChargeObject = paymentIntent.getLatestCharge();
    Charge charge = Charge.retrieve(latestChargeObject, requestOptions);
    String receiptUrl = charge.getReceiptUrl();
    if (Objects.isNull(receiptUrl)) {
      throw new RuntimeException("Invalid receipt url");
    }
    return receiptUrl;
  }
}
