package codekata.checkout.service;

import java.util.UUID;

/**
 * Supplier for {@link UUID}
 */
public class UUIDSupplier {

    public String get() {
        return UUID.randomUUID().toString();
    }
}
