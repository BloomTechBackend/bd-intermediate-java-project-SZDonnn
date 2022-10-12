package com.amazon.ata.deliveringonourpromise.dao;

import com.amazon.ata.deliveringonourpromise.App;
import com.amazon.ata.deliveringonourpromise.ordermanipulationauthority.OrderManipulationAuthorityClient;
import com.amazon.ata.deliveringonourpromise.types.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderDaoTest {


    private OrderManipulationAuthorityClient omaClient = App.getOrderManipulationAuthorityClient();
    private OrderDao dao = new OrderDao(omaClient);

    @Test
    public void get_forKnownOrderId_returnsOrder() {
        // GIVEN
        String orderId = "111-7497023-2960775";

        // WHEN
        Order result = dao.get(orderId);

        // THEN
        Assertions.assertNotEquals(null, result, "PASSED");
    }

    @Test
    public void get_forUnknownOrderId_returnsNull() {
        // GIVEN
        String orderId = "900-0000000-0000000";

        // WHEN
        Order result = dao.get(orderId);

        // THEN
        Assertions.assertEquals(null, result, "PASSED");
    }
}
