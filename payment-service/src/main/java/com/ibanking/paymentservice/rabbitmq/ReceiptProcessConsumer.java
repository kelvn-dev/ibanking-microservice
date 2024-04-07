package com.ibanking.paymentservice.rabbitmq;

import com.ibanking.paymentservice.sendgrid.SendgridService;
import com.ibanking.paymentservice.stripe.payment.PaymentService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReceiptProcessConsumer {

  private final PaymentService paymentService;
  private final SendgridService sendgridService;

  @SneakyThrows({IOException.class})
  @RabbitListener(queues = "q.receipt-process")
  public void sendReceipt(ReceiptProcessDto dto) {
    URL url = new URL(dto.getReceiptUrl());
    url.openConnection();
    ByteArrayOutputStream result;
    try (InputStream inputStream = url.openStream()) {
      result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      for (int length; (length = inputStream.read(buffer)) != -1; ) {
        result.write(buffer, 0, length);
      }
    }
    String body = result.toString(StandardCharsets.UTF_8);
    sendgridService.sendReceipt(dto.getEmail(), body);
  }
}
