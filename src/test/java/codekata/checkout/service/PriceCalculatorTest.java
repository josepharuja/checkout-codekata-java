package codekata.checkout.service;

import codekata.checkout.CheckoutTestDataRepository;
import codekata.checkout.domain.AppliedDiscount;
import codekata.checkout.domain.Item;
import codekata.checkout.domain.Promotion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.SortedMap;
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
    public void calculateDiscountsWhenItemQuantityIsEqualToPromotionalQuantity() {
        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        final Item itemA = CheckoutTestDataRepository.itemA();
        final Item itemB = CheckoutTestDataRepository.itemB();
        final Item itemC = CheckoutTestDataRepository.itemC();
        final Item itemD = CheckoutTestDataRepository.itemD();

        basketItems.put(itemA, 3);
        basketItems.put(itemB, 2);

        basketItems.put(itemC, 1);
        basketItems.put(itemD, 1);

        SortedMap<Promotion, AppliedDiscount> promotionalDiscounts = priceCalculator.calculateDiscounts(basketItems, CheckoutTestDataRepository.getPromotionsForThisWeek());
        assertThat(promotionalDiscounts.size()).isEqualTo(2);

        AppliedDiscount appliedDiscountForItemA = promotionalDiscounts.entrySet().parallelStream()
                .filter(promotionAppliedDiscountEntry -> promotionAppliedDiscountEntry.getKey().getItem().equals(itemA)).findFirst().get().getValue();
        assertThat(appliedDiscountForItemA).isNotNull();
        assertThat(appliedDiscountForItemA.getDiscount()).isEqualTo(20.0);
        AppliedDiscount appliedDiscountForItemB = promotionalDiscounts.entrySet().parallelStream()
                .filter(promotionAppliedDiscountEntry -> promotionAppliedDiscountEntry.getKey().getItem().equals(itemB)).findFirst().get().getValue();
        assertThat(appliedDiscountForItemB).isNotNull();
        assertThat(appliedDiscountForItemB.getDiscount()).isEqualTo(15.0);

    }

    @Test
    public void calculateDiscountsWhenItemQuantityIsGreaterThanPromotionalQuantity() {
        final TreeMap<Item, Integer> basketItems = new TreeMap<>();
        final Item itemA = CheckoutTestDataRepository.itemA();
        final Item itemB = CheckoutTestDataRepository.itemB();
        final Item itemC = CheckoutTestDataRepository.itemC();
        final Item itemD = CheckoutTestDataRepository.itemD();

        basketItems.put(itemA, 4);
        basketItems.put(itemB, 3);

        basketItems.put(itemC, 1);
        basketItems.put(itemD, 1);

        SortedMap<Promotion, AppliedDiscount> promotionalDiscounts = priceCalculator.calculateDiscounts(basketItems, CheckoutTestDataRepository.getPromotionsForThisWeek());
        System.out.println(promotionalDiscounts);
        assertThat(promotionalDiscounts.size()).isEqualTo(2);

        AppliedDiscount appliedDiscountForItemA = promotionalDiscounts.entrySet().parallelStream()
                .filter(promotionAppliedDiscountEntry -> promotionAppliedDiscountEntry.getKey().getItem().equals(itemA)).findFirst().get().getValue();
        assertThat(appliedDiscountForItemA).isNotNull();
        assertThat(appliedDiscountForItemA.getDiscount()).isEqualTo(20.0);
        AppliedDiscount appliedDiscountForItemB = promotionalDiscounts.entrySet().parallelStream()
                .filter(promotionAppliedDiscountEntry -> promotionAppliedDiscountEntry.getKey().getItem().equals(itemB)).findFirst().get().getValue();
        assertThat(appliedDiscountForItemB).isNotNull();
        assertThat(appliedDiscountForItemB.getDiscount()).isEqualTo(15.0);

    }


}