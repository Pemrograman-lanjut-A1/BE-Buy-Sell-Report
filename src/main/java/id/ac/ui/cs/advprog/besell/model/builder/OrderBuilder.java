package id.ac.ui.cs.advprog.besell.model.builder;

import id.ac.ui.cs.advprog.besell.enums.OrderStatus;
import id.ac.ui.cs.advprog.besell.model.Order;
import lombok.Getter;

@Getter
public class OrderBuilder {
    // Required parameters
    private String buyerId;
    private String sellerId;

    // Optional parameters
    private String status;

    public OrderBuilder(String buyerId, String sellerId) {
        this.buyerId = buyerId;
        this.status = OrderStatus.WAITING_PAYMENT.getValue();
    }

    public OrderBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setBuyerId(String buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public OrderBuilder setSellerId(String sellerId) {
            this.sellerId = sellerId;
            return this;
    }

    public Order build() {
        return new Order(this);
    }
}
