package codekata.checkout.service;

import codekata.checkout.CheckoutTestDataRepository;
import codekata.checkout.domain.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorTest {

    private PriceCalculator priceCalculator;

    @Before
    public void setup() {
        priceCalculator = new PriceCalculator();
    }

    @After
    public void tearDown() {
        priceCalculator = null;
    }

    @Test
    public void calculateTotalWithNoPromotionsAndWithPromotions() {
        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        basketItems.put(CheckoutTestDataRepository.itemA(), 3);
        basketItems.put(CheckoutTestDataRepository.itemB(), 2);
        basketItems.put(CheckoutTestDataRepository.itemC(), 1);
        basketItems.put(CheckoutTestDataRepository.itemD(), 1);
        final Double calculateTotalWithoutPromotions = priceCalculator.calculateTotal(basketItems, Collections.emptyList());
        assertThat(calculateTotalWithoutPromotions).isEqualTo(245);

        final Double calculateTotalWithPromotions = priceCalculator.calculateTotal(basketItems, CheckoutTestDataRepository.getPromotionsForThisWeek());
        assertThat(calculateTotalWithPromotions).isEqualTo(210);
    }

    @Test
    public void calculateTotalWithPromotionsWhenPromotionQuantityLessThanItemQuantity() {
        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        basketItems.put(CheckoutTestDataRepository.itemA(), 4);
        basketItems.put(CheckoutTestDataRepository.itemB(), 3);
        final Double calculateTotal = priceCalculator.calculateTotal(basketItems, CheckoutTestDataRepository.getPromotionsForThisWeek());
        assertThat(calculateTotal).isEqualTo(255);
    }

    @Test
    public void calculateTotalWithPromotionsWhenPromotionQuantityEqualToItemQuantity() {
        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        basketItems.put(CheckoutTestDataRepository.itemA(), 3);
        basketItems.put(CheckoutTestDataRepository.itemB(), 2);
        final Double calculateTotal = priceCalculator.calculateTotal(basketItems, CheckoutTestDataRepository.getPromotionsForThisWeek());
        assertThat(calculateTotal).isEqualTo(175);
    }

    @Test
    public void calculateTotalWithPromotionsButBasketItemsNotInPromotionList() {
        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        basketItems.put(CheckoutTestDataRepository.itemC(), 3);
        final Double calculateTotal = priceCalculator.calculateTotal(basketItems, CheckoutTestDataRepository.getPromotionsForThisWeek());
        assertThat(calculateTotal).isEqualTo(60);
    }
}