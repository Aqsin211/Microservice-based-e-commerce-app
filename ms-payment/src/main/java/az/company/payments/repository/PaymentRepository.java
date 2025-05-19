package az.company.payments.repository;

import az.company.payments.dao.entity.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentRepository extends CrudRepository<PaymentEntity,Long> {
    Optional<PaymentEntity> getPaymentEntityByOrderId(Long orderId);
}
