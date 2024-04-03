package com.ibanking.paymentservice.transaction;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

  private final GoogleAuthenticator googleAuthenticator;

  public OtpService() {
    this.googleAuthenticator = new GoogleAuthenticator();
  }

  public String getOtpSecret() {
    return googleAuthenticator.createCredentials().getKey();
  }

  public int getTotpCode(String secret, long time) {
    return googleAuthenticator.getTotpPassword(secret, time);
  }

  public boolean verifyTotpCode(String secret, long time, int totpCode) {
    return googleAuthenticator.authorize(secret, totpCode, time);
  }
}
