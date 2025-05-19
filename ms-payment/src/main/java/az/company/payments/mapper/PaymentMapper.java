package az.company.payments.mapper;

import az.company.payments.dao.entity.PaymentEntity;
import az.company.payments.model.enums.PaymentStatus;
import az.company.payments.model.request.CreatePaymentRequest;
import az.company.payments.model.response.PaymentResponse;

import static az.company.payments.model.enums.PaymentStatus.SUCCESS;
import static java.time.LocalDateTime.now;

public enum PaymentMapper {
    PAYMENT_MAPPER;

    public PaymentEntity buildPaymentEntity(CreatePaymentRequest createPaymentRequest) {
        return PaymentEntity.builder()
                .paymentType(createPaymentRequest.getPaymentType())
                .amount(createPaymentRequest.getAmount())
                .paymentStatus(SUCCESS)
                .createdAt(now())
                .referenceNumber(createPaymentRequest.getReferenceNumber())
                .orderId(createPaymentRequest.getOrderId())
                .build();
    }

    public PaymentResponse buildPaymentResponse(PaymentEntity paymentEntity) {
        return PaymentResponse.builder()
                .id(paymentEntity.getId())
                .createdAt(paymentEntity.getCreatedAt())
                .status(paymentEntity.getPaymentStatus())
                .paymentType(paymentEntity.getPaymentType())
                .build();
    }
}
