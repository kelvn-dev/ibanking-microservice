package com.ibanking.paymentservice.transaction;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
  private final TransactionService transactionService;
  private final TransactionMapper transactionMapper;

  @PostMapping
  public ResponseEntity<?> create(
      @RequestHeader(name = "x-user-id") String userId, @Valid @RequestBody TransactionReqDto dto) {
    Transaction transaction = transactionService.create(userId, dto);
    return ResponseEntity.ok(transactionMapper.model2Dto(transaction));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> complete(
      @RequestHeader(name = "x-user-id") String userId,
      @PathVariable(name = "id") UUID transactionId,
      @Valid @RequestBody TransactionCompleteDto dto) {
    transactionService.complete(userId, transactionId, dto);
    return ResponseEntity.ok(null);
  }
}
