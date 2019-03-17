package codekata.checkout.service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UUIDSupplierTest {

    private static final String UUID_REG_EX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @Test
    public void get() {
        UUIDSupplier uuidSupplier = new UUIDSupplier();
        final String uniqueIdentifier = uuidSupplier.get();
        assertThat(uniqueIdentifier.matches(UUID_REG_EX)).isTrue();

    }
}