package com.ibanking.paymentservice.tuition;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tuition")
@RequiredArgsConstructor
public class TuitionController {
  private final TuitionService tuitionService;
  private final TuitionMapper tuitionMapper;

  @PostMapping
  public ResponseEntity<?> create(@Valid @RequestBody TuitionReqDto dto) {
    tuitionService.create(dto);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable(name = "id") UUID id) {
    Tuition tuition = tuitionService.getById(id, false);
    return ResponseEntity.ok(tuitionMapper.model2DtoIgnoreStudent(tuition));
  }
}
