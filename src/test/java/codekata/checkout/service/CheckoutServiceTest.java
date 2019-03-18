package codekata.checkout.service;

import codekata.checkout.CheckoutTestDataRepository;
import codekata.checkout.domain.Basket;
import codekata.checkout.domain.BasketState;
import codekata.checkout.domain.Item;
import codekata.checkout.domain.Promotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceTest {

    private CheckoutService checkoutService;

    @Mock
    private UUIDSupplier mockUUIDSupplier;

    @Mock
    private PriceCalculator mockPriceCalculator;

    @Before
    public void setup() {
        checkoutService = new CheckoutService(mockUUIDSupplier, mockPriceCalculator);
    }

    @After
    public void tearDown() {
        checkoutService = null;
    }

    @Test
    public void startCheckOutShouldInitialiseBasketWithProvidedAvailablePromotions() {

        final String basketUUID = CheckoutTestDataRepository.anUUID();
        final Basket basket = CheckoutTestDataRepository.anEmptyBasket();
        final List<Promotion> availablePromotions = CheckoutTestDataRepository.getPromotionsForThisWeek();

        when(mockUUIDSupplier.get()).thenReturn(basketUUID);

        final String basketUUIDReturned = checkoutService.startTransaction(availablePromotions);

        assertThat(basketUUIDReturned).isNotNull().isEqualTo(basketUUID);
        assertThat(checkoutService.getBasket(basketUUIDReturned)).isEqualTo(basket);
        assertThat(checkoutService.getBasket(basketUUIDReturned).getBasketState()).isEqualTo(BasketState.IN_PROGRESS);
        assertThat(checkoutService.getPromotions()).isEqualTo(availablePromotions);
        verify(mockUUIDSupplier).get();

    }


    @Test
    public void givenTransactionIsStartedScanItemShouldAddItemAndReturnBasketWithTotalPromotionsApplied() {

        final String basketUUID = CheckoutTestDataRepository.anUUID();
        final Basket basket = CheckoutTestDataRepository.anEmptyBasket();

        final List<Promotion> availablePromotions = CheckoutTestDataRepository.getPromotionsForThisWeek();

        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        basketItems.put(CheckoutTestDataRepository.itemA(), 3);
        basketItems.put(CheckoutTestDataRepository.itemB(), 2);


        when(mockUUIDSupplier.get()).thenReturn(basketUUID);
        when(mockPriceCalculator.calculateTotal(basketItems, availablePromotions)).thenReturn(175.0);

        final String basketUUIDReturned = checkoutService.startTransaction(availablePromotions);
        assertThat(basketUUIDReturned).isNotNull().isEqualTo(basketUUID);

        assertThat(checkoutService.getBasket(basketUUIDReturned)).isEqualTo(basket);
        assertThat(checkoutService.getPromotions()).isEqualTo(availablePromotions);

        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemB());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());
        checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemB());
        final Basket basketReturned = checkoutService.scanItem(basketUUID, CheckoutTestDataRepository.itemA());


        assertThat(basketReturned).isNotNull().isEqualTo(CheckoutTestDataRepository.aBasketWithThreeItemAandTwoItemBWithTotal());
        assertThat(basketReturned.getBasketState()).isNotNull().isEqualTo(BasketState.IN_PROGRESS);

        verify(mockUUIDSupplier).get();
        verify(mockPriceCalculator, times(5)).calculateTotal(basketReturned.getBasketItems(), availablePromotions);

        checkoutService.completeCheckOut(basketUUID);
        assertThat(basketReturned.getBasketState()).isNotNull().isEqualTo(BasketState.COMPLETE);

    }


}
