package com.ibanking.paymentservice.common;

import com.ibanking.paymentservice.exception.NotFoundException;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public abstract class BaseService<M extends BaseModel, R extends JpaRepository<M, UUID>> {

  protected final Class<M> modelClass =
      (Class<M>)
          ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  protected final R repository;

  public M getById(UUID id, boolean noException) {
    M model = repository.findById(id).orElse(null);
    if (Objects.isNull(model) && !noException) {
      throw new NotFoundException(modelClass, "id", id.toString());
    }
    return model;
  }

  public void deleteById(UUID id) {
    M model = this.getById(id, false);
    repository.delete(model);
  }
}
