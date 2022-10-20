package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.orderfulfillmentservice.OrderFulfillmentServiceClient;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Promise;
import com.amazon.ata.ordermanipulationauthority.OrderResult;
import com.amazon.ata.ordermanipulationauthority.OrderResultItem;
import com.amazon.ata.ordermanipulationauthority.OrderShipment;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for Promises.
 */
public class PromiseDao implements ReadOnlyDao<String, List<Promise>> {
    private List<DeliveryPromiseServiceClient> dpsClients = new ArrayList<>();
    private List<OrderManipulationAuthorityClient> omaClients = new ArrayList<>();
    private List<OrderFulfillmentServiceClient> ofsClients = new ArrayList<>();
    private List<Promise> promises = new ArrayList<>();
    private ZonedDateTime result = null;

    /**
     * PromiseDao constructor, accepting service clients for DPS and OMA.
     * @param dpsClient DeliveryPromiseServiceClient for DAO to access DPS
     * @param omaClient OrderManipulationAuthorityClient for DAO to access OMA
     * @param ofsClient OrderFulfillmentServiceClient for DAO to access OFS
     */
    public PromiseDao(DeliveryPromiseServiceClient dpsClient, OrderManipulationAuthorityClient omaClient,
                        OrderFulfillmentServiceClient ofsClient) {
        this.dpsClients.add(dpsClient);
        this.omaClients.add(omaClient);
        this.ofsClients.add(ofsClient);
    }

    /**
     * PromiseDao constructor, accepting service clients for DPS and OMA.
     * @param dpsClients DeliveryPromiseServiceClient for DAO to access DPS
     * @param omaClients OrderManipulationAuthorityClient for DAO to access OMA
     * @param ofsClients OrderFulfillmentServiceClient for DAO to access OFS
     */
    public PromiseDao(List<DeliveryPromiseServiceClient> dpsClients, List<OrderManipulationAuthorityClient> omaClients,
                      List<OrderFulfillmentServiceClient> ofsClients) {
        this.dpsClients.addAll(dpsClients);
        this.omaClients.addAll(omaClients);
        this.ofsClients.addAll(ofsClients);
    }
    /**
     * Returns a list of all Promises associated with the given order item ID.
     * @param customerOrderItemId the order item ID to fetch promise for
     * @return a List of promises for the given order item ID
     */
    @Override
    public List<Promise> get(String customerOrderItemId) {
        // fetch Promise from Delivery Promise Service. If exists, add to list of Promises to return.
        // DeliveryPromiseServiceClient
        for (DeliveryPromiseServiceClient dpsClient : dpsClients) {
            Promise promise = dpsClient.getPromiseByOrderItemId(customerOrderItemId);
            isPromiseValid(promise, customerOrderItemId);
        }

        // fetch Promise from OrderFulfillment Promise Service. If exists, add to list of Promises to return.
        // OrderFulfillmentServiceClient
        for (OrderFulfillmentServiceClient ofsClient : ofsClients) {
            Promise promise = ofsClient.getPromiseByOrderItemId(customerOrderItemId);
            isPromiseValid(promise, customerOrderItemId);
        }
        return promises;
    }
    private void isPromiseValid(Promise promise, String customerOrderItemId) {
        // Fetch the delivery date, so we can add to any promises that we find
        ZonedDateTime itemDeliveryDate = getDeliveryDateForOrderItem(customerOrderItemId);
        if (promise != null) {
            // Set delivery date
            promise.setDeliveryDate(itemDeliveryDate);
            promises.add(promise);
        }
    }

    /**
     * Fetches the delivery date of the shipment containing the order item specified by the given order item ID,
     * if there is one.
     * If the order item ID doesn't correspond to a valid order item, or if the shipment hasn't been delivered
     * yet, return null.
     */
    private ZonedDateTime getDeliveryDateForOrderItem(String customerOrderItemId) {
        OrderResultItem orderResultItem = null;
        OrderResult orderResult = null;
        for (OrderManipulationAuthorityClient omaClient : omaClients) {
            orderResultItem = omaClient.getCustomerOrderItemByOrderItemId(customerOrderItemId);
            orderResult = omaClient.getCustomerOrderByOrderId(orderResultItem.getOrderId());
        }
        if (orderResultItem == null) {
            return result;
        }
        for (OrderShipment shipment : orderResult.getOrderShipmentList()) {
            for (OrderShipment.ShipmentItem shipmentItem : shipment.getCustomerShipmentItems()) {
                if (shipmentItem.getCustomerOrderItemId().equals(customerOrderItemId)) {
                    result = shipment.getDeliveryDate();
                    break;
                }
            }
        }
        return result;
    }
}
