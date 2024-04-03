package com.ibanking.paymentservice.sendgrid;

import com.ibanking.paymentservice.client.user.UserResDto;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.ServiceUnavailableException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class SendgridService {
  private final SendgridPropConfig sendgridPropConfig;
  private final SendGrid sendGrid;
  private final Email sender;

  public SendgridService(SendgridPropConfig sendgridPropConfig, SendGrid sendGrid) {
    this.sendgridPropConfig = sendgridPropConfig;
    this.sendGrid = sendGrid;
    this.sender = new Email(sendgridPropConfig.getEmailSender());
  }

  @SneakyThrows
  public void send(Mail mail) {
    Request request = new Request();
    Response response;
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      response = sendGrid.api(request);
      if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
        return;
      }
      throw new ServiceUnavailableException(response.getBody());
    } catch (IOException e) {
      throw new ServiceUnavailableException(e.getMessage());
    }
  }

  public void sendWithTemplate(
      String emailTo, String templateId, Map<String, String> templateData) {
    Email receiver = new Email(emailTo);
    Content content = new Content("text/html", "Empty");

    // https://stackoverflow.com/questions/53111157/sendgrid-v3-substitutions-may-not-be-used-with-dynamic-templating
    Mail mail = new Mail(sender, null, receiver, content);
    mail.setReplyTo(sender);
    mail.setTemplateId(templateId);
    templateData.forEach(
        (key, value) -> mail.personalization.get(0).addDynamicTemplateData(key, value));

    send(mail);
  }

  public void sendWithoutTemplate(String emailTo, String body) {
    Email receiver = new Email(emailTo);
    Content content = new Content("text/html", body);
    Mail mail = new Mail(sender, "success tuition fee", receiver, content);
    mail.setReplyTo(sender);
    send(mail);
  }

  public void sendOtpVerification(UserResDto user, int otpCode) {
    Map<String, String> templateData = new HashMap<>();
    templateData.put("full_name", user.getFullName());
    templateData.put("otp_code", String.valueOf(otpCode));
    sendWithTemplate(user.getEmail(), SendgridTemplate.OTP_VERIFICATION.getId(), templateData);
  }
}
