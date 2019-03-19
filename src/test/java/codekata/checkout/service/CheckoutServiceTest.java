package codekata.checkout.service;

import codekata.checkout.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.TreeMap;

import static codekata.checkout.CheckoutTestDataRepository.*;
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

        final String basketUUID = anUUID();
        final Basket basket = anEmptyBasket();
        final List<Promotion> availablePromotions = getPromotionsForThisWeek();

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

        final String basketUUID = anUUID();
        final Basket basket = anEmptyBasket();

        final List<Promotion> availablePromotions = getPromotionsForThisWeek();

        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        basketItems.put(itemA(), 3);
        basketItems.put(itemB(), 2);


        TreeMap<Promotion, AppliedDiscount> appliedDiscounts = new TreeMap<>();

        appliedDiscounts.put(getItemAPromotion(),new AppliedDiscount(3,20.0));
        appliedDiscounts.put(getItemBPromotion(),new AppliedDiscount(2,15.0));


        when(mockUUIDSupplier.get()).thenReturn(basketUUID);
        when(mockPriceCalculator.calculateDiscounts(basketItems, availablePromotions)).thenReturn(appliedDiscounts);

        final String basketUUIDReturned = checkoutService.startTransaction(availablePromotions);
        assertThat(basketUUIDReturned).isNotNull().isEqualTo(basketUUID);

        assertThat(checkoutService.getBasket(basketUUIDReturned)).isEqualTo(basket);
        assertThat(checkoutService.getPromotions()).isEqualTo(availablePromotions);

        checkoutService.scanItem(basketUUID, itemA());
        checkoutService.scanItem(basketUUID, itemB());
        checkoutService.scanItem(basketUUID, itemA());
        checkoutService.scanItem(basketUUID, itemB());
        final Basket basketReturned = checkoutService.scanItem(basketUUID, itemA());


        assertThat(basketReturned).isNotNull().isEqualTo(aBasketWithThreeItemAandTwoItemB());
        assertThat(basketReturned.getBasketState()).isNotNull().isEqualTo(BasketState.IN_PROGRESS);

        verify(mockUUIDSupplier).get();
        verify(mockPriceCalculator, times(5)).calculateDiscounts(basketReturned.getBasketItems(), availablePromotions);

        checkoutService.completeCheckOut(basketUUID);
        assertThat(basketReturned.getBasketState()).isNotNull().isEqualTo(BasketState.COMPLETE);

    }


}
