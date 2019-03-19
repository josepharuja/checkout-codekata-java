package codekata.checkout.service;

import codekata.checkout.domain.AppliedDiscount;
import codekata.checkout.domain.Basket;
import codekata.checkout.domain.BasketState;
import codekata.checkout.domain.Promotion;
import org.junit.Test;

import java.util.TreeMap;

import static codekata.checkout.CheckoutTestDataRepository.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    @Test
    public void verifyThatBasketIncrementsItemCountButNotAnotherItemWhenSameItemIsAddedAgain() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);

        basket.addItem(itemA());
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basket.getBasketItems().get(itemA())).isEqualTo(1);
        assertThat(basket).isEqualTo(aBasketWithItemA());


        basket.addItem(itemA());
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basket.getBasketItems().get(itemA())).isEqualTo(2);
        assertThat(basket).isEqualTo(aBasketWithTwoItemA());

        basket.addItem(itemA());
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basket.getBasketItems().get(itemA())).isEqualTo(3);
        assertThat(basket).isEqualTo(aBasketWithThreeItemA());
    }

    @Test
    public void verifyThatBasketKeepsBothItemsWhenSeparateItemsAreAdded() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);

        basket.addItem(itemA());
        basket.addItem(itemB());

        basket.addItem(itemA());
        basket.addItem(itemA());

        assertThat(basket.getBasketItems().size()).isEqualTo(2);
        assertThat(basket.getBasketItems().get(itemB())).isEqualTo(1);
        assertThat(basket.getBasketItems().get(itemA())).isEqualTo(3);
        assertThat(basket).isEqualTo(aBasketWithThreeItemAandOneItemB());

    }

    @Test
    public void calculateTotalWithNoAppliedDiscount() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);

        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemB());


        assertThat(basket.getBasketItems().size()).isEqualTo(2);
        assertThat(basket.getBasketItems().get(itemB())).isEqualTo(1);
        assertThat(basket.getBasketItems().get(itemA())).isEqualTo(3);
        assertThat(basket).isEqualTo(aBasketWithThreeItemAandOneItemB());
        assertThat(basket.getTotal()).isEqualTo(180.0);

    }

    @Test
    public void calculateTotalWithAppliedDiscounts() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);

        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemB());
        TreeMap<Promotion, AppliedDiscount> appliedDiscounts = new TreeMap<>();
        appliedDiscounts.put(getItemAPromotion(),new AppliedDiscount(3,20.0));
        basket.setAppliedDiscounts(appliedDiscounts);


        assertThat(basket.getBasketItems().size()).isEqualTo(2);
        assertThat(basket.getBasketItems().get(itemB())).isEqualTo(1);
        assertThat(basket.getBasketItems().get(itemA())).isEqualTo(3);
        assertThat(basket).isEqualTo(aBasketWithThreeItemAandOneItemB());
        assertThat(basket.getTotal()).isEqualTo(160.0);

    }

}