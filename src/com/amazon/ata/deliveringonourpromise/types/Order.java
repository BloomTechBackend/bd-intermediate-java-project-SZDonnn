package com.amazon.ata.deliveringonourpromise.types;

import com.amazon.ata.ordermanipulationauthority.OrderCondition;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a customer order.
 *
 * Construct an Order via the {@code Order.builder()...build();} pattern,
 * for example:
 *
 * <pre>{@code
 *   Order order = Order.builder()
 *                      .with(OrderId(orderId)
 *                      ...
 *                      .withCondition(condition)
 *                      .build();
 * }</pre>
 *
 * Explanation of fields:
 * * orderId: the unique identifier for this order
 * * customerId: the identifier for the customer who placed the order
 * * marketplaceId: the identifier corresponding to the marketplace the order was placed in
 * * condition: the current state of the Order (e.g. PENDING, CLOSED). See {@link OrderCondition}.
 * * customerOrderItemList: the list of items and their quantities in this order
 * * shipOption: which shipping option the customer selected for this order
 * * orderDate: the timestamp of when the order was placed
 */
public class Order {
    private final String orderId;
    private final String customerId;
    private final String marketplaceId;
    private final OrderCondition condition;
    private final List<OrderItem> customerOrderItemList;
    private final String shipOption;
    private final ZonedDateTime orderDate;

    Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.marketplaceId = builder.marketplaceId;
        this.condition = builder.condition;
        this.customerOrderItemList = builder.customerOrderItemList;
        this.shipOption = builder.shipOption;
        this.orderDate = builder.orderDate;
    }

    /**
     * Returns a new Order.Builder object for constructing an Order.
     * @return new builder ready for constructing an Order
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getOrderId() { return orderId; }

    public String getCustomerId() {
        return customerId;
    }

    public String getMarketplaceId() {
        return marketplaceId;
    }

    public OrderCondition getCondition() { return condition; }

    /**
     * Returns a list containing all the order items in this order.
     *
     * @return a list containing all the order items in this order
     */
    public List<OrderItem> getCustomerOrderItemList() {
        List<OrderItem> copies = new ArrayList<>(customerOrderItemList.size());
        for (int i = 0; i < customerOrderItemList.size(); i++) {
            copies.add(i, new OrderItem.Builder()
                    .withCustomerOrderItemId(customerOrderItemList.get(i).getCustomerOrderItemId())
                    .withOrderId(customerOrderItemList.get(i).getOrderId())
                    .withAsin(customerOrderItemList.get(i).getAsin())
                    .withMerchantId(customerOrderItemList.get(i).getMerchantId())
                    .withQuantity(customerOrderItemList.get(i).getQuantity())
                    .withTitle(customerOrderItemList.get(i).getTitle())
                    .withIsConfidenceTracked(customerOrderItemList.get(i).isConfidenceTracked())
                    .withConfidence(customerOrderItemList.get(i).getConfidence())
                    .build()
            );
        }
        return copies;
    }

    public String getShipOption() {
        return shipOption;
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
               "orderId='" + orderId + '\'' +
               ", customerId='" + customerId + '\'' +
               ", marketplaceId='" + marketplaceId + '\'' +
               ", condition=" + condition +
               ", customerOrderItemList=" + customerOrderItemList +
               ", shipOption='" + shipOption + '\'' +
               ", orderDate=" + orderDate +
               '}';
    }

    /**
     * Builder for Orders. See Order documentation.
     */
    public static class Builder {
        private String orderId;
        private String customerId;
        private String marketplaceId;
        private OrderCondition condition;
        private List<OrderItem> customerOrderItemList;
        private String shipOption;
        private ZonedDateTime orderDate;

        //CHECKSTYLE:OFF:HiddenField
        //CHECKSTYLE:OFF:JavadocMethod
        public Builder withOrderId(final String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withCustomerId(final String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withMarketplaceId(final String marketplaceId) {
            this.marketplaceId = marketplaceId;
            return this;
        }

        public Builder withCondition(final OrderCondition condition) {
            this.condition = condition;
            return this;
        }

        /**
         * Adds the given order item to the list of order items in this order.
         *
         * @param customerOrderItemList {@code List<OrderItem>} containing the order items to add to the order
         * @return updated Builder
         */
        public Builder withCustomerOrderItemList(final List<OrderItem> customerOrderItemList) {
            List<OrderItem> copies = new ArrayList<>(customerOrderItemList.size());
            for (int i = 0; i < customerOrderItemList.size(); i++) {
                copies.add(i, new OrderItem.Builder()
                    .withCustomerOrderItemId(customerOrderItemList.get(i).getCustomerOrderItemId())
                    .withOrderId(customerOrderItemList.get(i).getOrderId())
                    .withAsin(customerOrderItemList.get(i).getAsin())
                    .withMerchantId(customerOrderItemList.get(i).getMerchantId())
                    .withQuantity(customerOrderItemList.get(i).getQuantity())
                    .withTitle(customerOrderItemList.get(i).getTitle())
                    .withIsConfidenceTracked(customerOrderItemList.get(i).isConfidenceTracked())
                    .withConfidence(customerOrderItemList.get(i).getConfidence())
                    .build()
                );
            }
            this.customerOrderItemList = copies;
            return this;
        }

        public Builder withShipOption(final String shipOption) {
            this.shipOption = shipOption;
            return this;
        }

        public Builder withOrderDate(final ZonedDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }
        //CHECKSTYLE:ON:JavadocMethod
        //CHECKSTYLE:ON:HiddenField

        /**
         * Builds the Order object from the current Builder state.
         *
         * @return constructed Order object
         */
        public Order build() {
            return new Order(this);
        }
    }
}
