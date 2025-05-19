package az.company.orders.mapper;

import az.company.orders.model.client.response.PaymentResponse;
import az.company.orders.model.client.response.ProductResponse;
import az.company.orders.dao.entity.OrderEntity;
import az.company.orders.model.request.CreateOrderRequest;
import az.company.orders.model.response.OrderResponse;

import static az.company.orders.model.enums.OrderStatus.PENDING;
import static java.time.LocalDateTime.now;

public enum OrderMapper {
    ORDER_MAPPER;

    public OrderEntity buildOrderEntity(CreateOrderRequest createOrderRequest) {
        return OrderEntity.builder()
                .productId(createOrderRequest.getProductId())
                .quantity(createOrderRequest.getQuantity())
                .status(PENDING)
                .createdAt(now())
                .build();
    }

    public OrderResponse buildOrderResponse(OrderEntity orderEntity, ProductResponse productResponse, PaymentResponse paymentResponse) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .productId(orderEntity.getProductId())
                .amount(orderEntity.getAmount())
                .quantity(orderEntity.getQuantity())
                .createdAt(orderEntity.getCreatedAt())
                .status(orderEntity.getStatus())
                .productResponse(productResponse)
                .paymentResponse(paymentResponse)
                .build();
    }
}
