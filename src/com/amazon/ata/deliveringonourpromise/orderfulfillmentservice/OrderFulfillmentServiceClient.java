package com.amazon.ata.deliveringonourpromise.orderfulfillmentservice;

import com.amazon.ata.deliveringonourpromise.interfaces.GetPromiseService;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.orderfulfillmentservice.OrderFulfillmentService;
import com.amazon.ata.orderfulfillmentservice.OrderPromise;

public class OrderFulfillmentServiceClient implements GetPromiseService {
    private OrderFulfillmentService ofService;

    /**
     * Create new client that calls DPS with the given service object.
     *
     * @param ofService The OrderFulfillmentService that this client will call.
     */
    public OrderFulfillmentServiceClient(OrderFulfillmentService ofService) {
        this.ofService = ofService;
    }

    /**
     * Fetches the Promise for the given order item ID.
     *
     * @param customerOrderItemId String representing the order item ID to fetch the order for.
     * @return the Promise for the given order item ID.
     */
    @Override
    public Promise getPromiseByOrderItemId(String customerOrderItemId) {
        OrderPromise orderPromise = ofService.getOrderPromise(customerOrderItemId);

        if (orderPromise == null) {
            return null;
        }

        return Promise.builder()
                .withCustomerOrderItemId(orderPromise.getCustomerOrderItemId())
                .withAsin(orderPromise.getAsin())
                .withIsActive(orderPromise.isActive())
                .withPromiseEffectiveDate(orderPromise.getPromiseEffectiveDate())
                .withPromiseLatestArrivalDate(orderPromise.getPromiseLatestArrivalDate())
                .withPromiseLatestShipDate(orderPromise.getPromiseLatestShipDate())
                .withDeliveryDate(null)
                .withPromiseProvidedBy(orderPromise.getPromiseProvidedBy())
                .build();
    }
}
