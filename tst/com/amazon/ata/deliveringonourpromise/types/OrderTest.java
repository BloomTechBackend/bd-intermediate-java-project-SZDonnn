package com.amazon.ata.deliveringonourpromise.types;

import com.amazon.ata.ordermanipulationauthority.OrderCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderTest {
    private String orderId;
    private String customerId;
    private String marketplaceId;
    private OrderCondition condition;
    private List<OrderItem> customerOrderItemList = new ArrayList<>();
    private String shipOption;
    private ZonedDateTime orderDate;

    @Test
    public void withOrderId_whenSet_returnsOrderId() {
        //GIVEN
        String setOrderId = "123";
        Order order = Order.builder()
                .withOrderId(setOrderId)
                .build();

        //WHEN
        String result = order.getOrderId();

        //THEN
        Assertions.assertEquals(result, "123");
    }
}
