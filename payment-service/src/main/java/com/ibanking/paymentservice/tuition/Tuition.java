package com.ibanking.paymentservice.tuition;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ibanking.paymentservice.common.BaseModel;
import com.ibanking.paymentservice.student.Student;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "tuition")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class Tuition extends BaseModel {

  @Column(name = "charges", nullable = false)
  private double charges;

  @Column(name = "is_paid", nullable = false)
  private boolean isPaid;

  @Column(name = "semester_year", columnDefinition = "SMALLINT", nullable = false)
  private short semesterYear;

  @Column(name = "semester_code", nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private SemesterCode semesterCode;

  @Column(name = "student_id", columnDefinition = "uuid", nullable = false)
  private UUID studentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", insertable = false, updatable = false)
  @JsonBackReference
  private Student student;
}
