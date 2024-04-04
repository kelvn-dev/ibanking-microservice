package com.ibanking.paymentservice.stripe;

import com.stripe.Stripe;
import com.stripe.net.RequestOptions;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class StripeService {

  private final StripePropConfig stripePropConfig;
  protected RequestOptions requestOptions;

  @PostConstruct
  public void init() {
    Stripe.apiKey = stripePropConfig.getSecretKey();
    requestOptions = RequestOptions.builder().setMaxNetworkRetries(3).build();
  }
}
