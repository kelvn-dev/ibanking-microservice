package com.ibanking.paymentservice.transaction;

import com.ibanking.paymentservice.common.BaseModel;
import com.ibanking.paymentservice.tuition.Tuition;
import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "app_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
public class Transaction extends BaseModel {

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private TransactionStatus status;

  @Column(name = "otp_secret", nullable = false)
  private String otpSecret;

  @Column(name = "otp_expiry_time", nullable = false)
  private long otpExpiryTime;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Column(name = "tuition_id", columnDefinition = "uuid", nullable = false)
  private UUID tuitionId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tuition_id", insertable = false, updatable = false)
  private Tuition tuition;
}
