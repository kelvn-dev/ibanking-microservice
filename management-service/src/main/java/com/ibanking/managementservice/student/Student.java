package com.ibanking.managementservice.student;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ibanking.managementservice.common.BaseModel;
import com.ibanking.managementservice.tuition.Tuition;
import jakarta.persistence.*;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Student extends BaseModel {

  @Column(name = "student_id")
  private String studentId;

  @Column(name = "full_name")
  private String fullName;

  @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, orphanRemoval = true)
  @JsonManagedReference
  private Set<Tuition> tuition;
}
