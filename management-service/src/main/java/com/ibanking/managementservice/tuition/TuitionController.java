package com.ibanking.managementservice.tuition;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tuition")
@RequiredArgsConstructor
public class TuitionController {
  private final TuitionService tuitionService;

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody TuitionReqDto dto) {
    tuitionService.create(dto);
    return ResponseEntity.ok(null);
  }
}
