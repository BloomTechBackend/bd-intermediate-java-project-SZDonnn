package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.deliverypromiseservice.DeliveryPromiseServiceClient;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderDaoTest {

    private OrderDao dao;
    private OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();

    @Test
    public void get_forKnownOrderId_returnsOrder() {
        // GIVEN
        String orderId = "111-7497023-2960775";
        dao = new OrderDao(omaClient);

        // WHEN
        Order result = dao.get(orderId);

        // THEN
        Assertions.assertNotEquals(null, result, "PASSED");
    }
}
