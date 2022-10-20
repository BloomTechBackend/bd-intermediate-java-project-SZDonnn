package com.amazon.ata.deliveringonourpromise.interfaces;

import com.amazon.ata.deliveringonourpromise.types.Promise;

/**
 * Clients interface to abstract calls.
 */
public interface GetPromiseService {
    /**
     * Get object method to be implemented.
     * @param id Order Id
     * @return Object Promise object
     */
    Promise getPromiseByOrderItemId(String id);
}
