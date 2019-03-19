package codekata.checkout;

import codekata.checkout.domain.Basket;
import codekata.checkout.domain.BasketState;
import codekata.checkout.service.CheckoutService;
import codekata.checkout.service.PriceCalculator;
import codekata.checkout.service.UUIDSupplier;
import org.junit.Test;

import java.util.Collections;

import static codekata.checkout.CheckoutTestDataRepository.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckOutIntegrationTest {

    private final UUIDSupplier uuidSupplier = new UUIDSupplier();
    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    public void validateCheckoutWithPromotions() {
        CheckoutService checkoutService = new CheckoutService(uuidSupplier, priceCalculator);
        final String basketUUID = checkoutService.startTransaction(getPromotionsForThisWeek());


        checkoutService.scanItem(basketUUID, itemC());
        checkoutService.scanItem(basketUUID, itemA());

        checkoutService.scanItem(basketUUID, itemB());
        checkoutService.scanItem(basketUUID, itemB());


        checkoutService.scanItem(basketUUID, itemD());


        checkoutService.scanItem(basketUUID, itemA());
        checkoutService.scanItem(basketUUID, itemA());
        checkoutService.scanItem(basketUUID, itemA());

        final Basket basket = checkoutService.getBasket(basketUUID);
        assertThat(basket.getTotal()).isNotNull().isEqualTo(260);
        assertThat(basket.getBasketState()).isEqualTo(BasketState.IN_PROGRESS);
        final Basket basketOnCompleteCheckOut = checkoutService.completeCheckOut(basketUUID);
        assertThat(basketOnCompleteCheckOut.getBasketState()).isEqualTo(BasketState.COMPLETE);


    }

    @Test
    public void validateCheckoutWithOutPromotions() {
        CheckoutService checkoutService = new CheckoutService(uuidSupplier, priceCalculator);
        final String baseketUUID = checkoutService.startTransaction(Collections.emptyList());

        checkoutService.scanItem(baseketUUID, itemB());
        checkoutService.scanItem(baseketUUID, itemA());

        checkoutService.scanItem(baseketUUID, itemB());


        checkoutService.scanItem(baseketUUID, itemC());
        checkoutService.scanItem(baseketUUID, itemD());

        checkoutService.scanItem(baseketUUID, itemA());
        checkoutService.scanItem(baseketUUID, itemA());

        final Basket basket = checkoutService.getBasket(baseketUUID);
        assertThat(basket.getTotal()).isNotNull().isEqualTo(245);
        assertThat(basket.getBasketState()).isEqualTo(BasketState.IN_PROGRESS);
        final Basket basketOnCompleteCheckOut = checkoutService.completeCheckOut(baseketUUID);
        assertThat(basketOnCompleteCheckOut.getBasketState()).isEqualTo(BasketState.COMPLETE);

    }


}
