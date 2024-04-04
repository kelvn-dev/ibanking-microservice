package com.ibanking.paymentservice.stripe.payment;

import com.ibanking.paymentservice.tuition.TuitionResDto;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe/payment")
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity<?> createPayment(@RequestBody TuitionResDto dto) {
    Session session = paymentService.createPayment(dto);
    PaymentResDto resDto =
        PaymentResDto.builder()
            .sessionId(session.getId())
            .sessionUrl(session.getUrl())
            .expiresAt(session.getExpiresAt())
            .build();
    return ResponseEntity.ok(resDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> sendReceipt(@PathVariable(name = "id") String id) {
    paymentService.sendReceipt(id);
    return ResponseEntity.ok(null);
  }
}
