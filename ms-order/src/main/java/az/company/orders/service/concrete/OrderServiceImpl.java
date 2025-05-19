package az.company.orders.service.concrete;

import az.company.orders.client.PaymentClient;
import az.company.orders.client.ProductClient;
import az.company.orders.model.client.request.ReduceQuantityRequest;
import az.company.orders.model.client.response.PaymentResponse;
import az.company.orders.model.client.response.ProductResponse;
import az.company.orders.dao.entity.OrderEntity;
import az.company.orders.dao.repository.OrderRepository;
import az.company.orders.exception.NotFoundException;
import az.company.orders.model.request.CreateOrderRequest;
import az.company.orders.model.response.OrderResponse;
import az.company.orders.service.abstraction.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static az.company.orders.mapper.OrderMapper.ORDER_MAPPER;
import static az.company.orders.mapper.PaymentMapper.PAYMENT_MAPPER;
import static az.company.orders.model.enums.ErrorMessage.ORDER_NOT_FOUND;
import static az.company.orders.model.enums.OrderStatus.APPROVED;
import static az.company.orders.model.enums.OrderStatus.REJECTED;
import static java.lang.String.format;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductClient productClient, PaymentClient paymentClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
    }

    @Override
    @Transactional
    public void createOrder(CreateOrderRequest createOrderRequest) {
        OrderEntity orderEntity = ORDER_MAPPER.buildOrderEntity(createOrderRequest);

        ProductResponse productResponse = productClient.getProductById(createOrderRequest.getProductId());

        BigDecimal totalAmount = productResponse.getPrice().multiply(BigDecimal.valueOf(createOrderRequest.getQuantity()));
        orderEntity.setAmount(totalAmount);

        ReduceQuantityRequest reduceQuantityRequest = new ReduceQuantityRequest(
                createOrderRequest.getProductId(),
                createOrderRequest.getQuantity()
        );

        orderRepository.save(orderEntity);
        productClient.reduceQuantity(reduceQuantityRequest);
        try {
            paymentClient.pay(PAYMENT_MAPPER.buildCreatePaymentRequest(createOrderRequest, orderEntity, totalAmount));
            orderEntity.setStatus(APPROVED);
        } catch (Exception exception) {
            orderEntity.setStatus(REJECTED);
        }
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format(
                        ORDER_NOT_FOUND.getMessage(),
                        id
                )));
        ProductResponse productResponse = productClient.getProductById(orderEntity.getProductId());
        PaymentResponse paymentResponse = paymentClient.getPaymentByOrderId(orderEntity.getId());
        return ORDER_MAPPER.buildOrderResponse(orderEntity, productResponse, paymentResponse);
    }
}
