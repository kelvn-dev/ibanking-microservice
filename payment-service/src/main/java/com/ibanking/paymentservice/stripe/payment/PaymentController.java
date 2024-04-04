// package com.ibanking.paymentservice.stripe.payment;
//
// import com.ibanking.paymentservice.tuition.Tuition;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// @RestController
// @RequestMapping("/api/v1/stripe/payment")
// @RequiredArgsConstructor
// public class PaymentController {
//  private final PaymentService paymentService;
//
//  @PostMapping("/{id}")
//  public ResponseEntity<?> createPayment(
//      @PathVariable(name = "id") String customerId, @RequestBody Tuition tuition) {
//    paymentService.createPaymentIntent(customerId, tuition);
//    return ResponseEntity.ok(null);
//  }
// }
