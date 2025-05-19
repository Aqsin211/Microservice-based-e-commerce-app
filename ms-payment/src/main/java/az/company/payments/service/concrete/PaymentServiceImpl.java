package az.company.payments.service.concrete;

import az.company.payments.dao.entity.PaymentEntity;
import az.company.payments.exception.NotFoundException;
import az.company.payments.model.request.CreatePaymentRequest;
import az.company.payments.model.response.PaymentResponse;
import az.company.payments.repository.PaymentRepository;
import az.company.payments.service.abstraction.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static az.company.payments.mapper.PaymentMapper.PAYMENT_MAPPER;
import static az.company.payments.model.enums.ErrorMessages.PAYMENT_NOT_FOUND;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public PaymentResponse pay(CreatePaymentRequest createPaymentRequest) {
        PaymentEntity paymentEntity = PAYMENT_MAPPER.buildPaymentEntity(createPaymentRequest);
        paymentRepository.save(paymentEntity);
        return PAYMENT_MAPPER.buildPaymentResponse(paymentEntity);
    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        return paymentRepository.getPaymentEntityByOrderId(orderId)
                .map(PAYMENT_MAPPER::buildPaymentResponse)
                .orElseThrow(() -> new NotFoundException(String.format(PAYMENT_NOT_FOUND.getMessage(), orderId)));
    }
}
