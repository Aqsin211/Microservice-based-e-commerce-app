package az.company.orders.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    ORDER_NOT_FOUND("Order not found with id: %s"),
    SERVER_ERROR("Unexpected error occurred"),
    CLIENT_ERROR("Client error occurred while making request");
    public final String message;
}
