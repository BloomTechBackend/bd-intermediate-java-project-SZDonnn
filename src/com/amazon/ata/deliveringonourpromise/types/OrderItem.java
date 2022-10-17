package com.amazon.ata.deliveringonourpromise.types;

import java.util.Objects;

/**
 * Represents an item in a customer's order.
 *
 * Construct an OrderItem via the {@code OrderItem.builder()...build();} pattern,
 * for example:
 *
 * <pre>{@code
 *   OrderItem orderItem = OrderItem.builder()
 *                             .withCustomerOrderItemId(orderItemId)
 *                             .withOrderId(orderId)
 *                             .withAsin(asin)
 *                             .withQuantity(quantity)
 *                             ...
 *                             .build();
 * }</pre>
 *
 * Explanation of fields:
 * * customerOrderItemId: unique identifier for the order-item
 * * orderId: the identifier for the order that this order-item belongs to
 * * asin: the ASIN of the item that the customer ordered
 * * merchantId: the identifier of the merchant selling the item to customer
 * * quantity: how many of the item this customer ordered in this order
 * * title: the customer-facing name of this item/product
 *
 */
public class OrderItem {
    private final String customerOrderItemId;
    private final String orderId;
    private final String asin;
    private final String merchantId;
    private final int quantity;
    private final String title;
    private final boolean isConfidenceTracked;
    private final int confidence;

    // for Builder's eyes only.
    OrderItem(Builder builder) {
        this.customerOrderItemId = builder.customerOrderItemId;
        this.orderId = builder.orderId;
        this.asin = builder.asin;
        this.merchantId = builder.merchantId;
        this.quantity = builder.quantity;
        this.title = builder.title;
        this.isConfidenceTracked = builder.isConfidenceTracked;
        this.confidence = builder.confidence;
    }

    /**
     * Returns a builder suitable for constructing an OrderItem.
     *
     * @return OrderItem.Builder to use for building OrderItem
     */
    public static Builder builder() {
        return new Builder();
    }

    public String getCustomerOrderItemId() {
        return customerOrderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAsin() {
        return asin;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isConfidenceTracked() {
        return isConfidenceTracked;
    }

    public int getConfidence() {
        return confidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.getQuantity() &&
               isConfidenceTracked == orderItem.isConfidenceTracked() &&
               confidence == orderItem.getConfidence() &&
               customerOrderItemId.equals(orderItem.getCustomerOrderItemId()) &&
               orderId.equals(orderItem.getOrderId()) &&
               Objects.equals(asin, orderItem.getAsin()) &&
               Objects.equals(merchantId, orderItem.getMerchantId()) &&
               Objects.equals(title, orderItem.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerOrderItemId, orderId, asin, merchantId,
                            quantity, title, isConfidenceTracked, confidence);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
               "customerOrderItemId='" + customerOrderItemId + '\'' +
               ", orderId='" + orderId + '\'' +
               ", asin='" + asin + '\'' +
               ", merchantId='" + merchantId + '\'' +
               ", quantity=" + quantity +
               ", title='" + title + '\'' +
               '}';
    }

    /**
     * Builder for OrderItems. See OrderItem documentation.
     */
    public static class Builder {
        private String customerOrderItemId;
        private String orderId;
        private String asin;
        private String merchantId;
        private int quantity;
        private String title;
        private boolean isConfidenceTracked;
        private int confidence;

        //CHECKSTYLE:OFF:HiddenField
        //CHECKSTYLE:OFF:JavadocMethod
        public Builder withCustomerOrderItemId(final String customerOrderItemId) {
            this.customerOrderItemId = customerOrderItemId;
            return this;
        }

        public Builder withOrderId(final String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withAsin(final String asin) {
            try {
                if (asin != null) {
                    this.asin = asin;
                }
            } catch (IllegalStateException e) {
                throw new NullPointerException();
            }
            return this;
        }

        public Builder withMerchantId(final String merchantId) {
            try {
                if (merchantId != null) {
                    this.merchantId = merchantId;
                }
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
            return this;
        }

        public Builder withQuantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder withIsConfidenceTracked(final boolean isConfidenceTracked) {
            this.isConfidenceTracked = isConfidenceTracked;
            return this;
        }

        public Builder withConfidence(final int confidence) {
            this.confidence = confidence;
            return this;
        }
        //CHECKSTYLE:ON:JavadocMethod
        //CHECKSTYLE:ON:HiddenField

        /**
         * Builds the OrderItem from the values previously set. Will throw IllegalStateException
         * if the OrderItem would be in an invalid state (null order item ID, for example).
         *
         * @return OrderItem constructed according to the
         */
        public OrderItem build() { return new OrderItem(this); }
    }
}
