package codekata.checkout;

import codekata.checkout.domain.Basket;
import codekata.checkout.service.BasketState;
import codekata.checkout.service.CheckoutService;
import codekata.checkout.service.PriceCalculator;
import codekata.checkout.service.UUIDSupplier;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckOutIntegrationTest {

    private final UUIDSupplier uuidSupplier = new UUIDSupplier();
    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    public void validateCheckoutWithPromotions() {
        CheckoutService checkoutService = new CheckoutService(uuidSupplier, priceCalculator);
        final String basketUUID = checkoutService.startTransaction(CheckoutTestDataRepository.getPromotionsForThisWeek());


        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemC());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());

        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemB());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemB());


        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemD());


        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());

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

        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemB());
        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemA());

        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemB());


        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemC());
        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemD());

        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemA());
        checkoutService.scanItem(baseketUUID, CheckoutTestDataRepository.itemA());

        final Basket basket = checkoutService.getBasket(baseketUUID);
        assertThat(basket.getTotal()).isNotNull().isEqualTo(245);
        assertThat(basket.getBasketState()).isEqualTo(BasketState.IN_PROGRESS);
        final Basket basketOnCompleteCheckOut = checkoutService.completeCheckOut(baseketUUID);
        assertThat(basketOnCompleteCheckOut.getBasketState()).isEqualTo(BasketState.COMPLETE);

    }


}
