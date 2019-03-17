package codekata.checkout.service;

import codekata.checkout.CheckoutTestDataRepository;
import codekata.checkout.domain.Basket;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest {

    @Test
    public void verifyThatBasketIncrementsItemCountButNotAnotherItemWhenSameItemIsAddedAgain() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);

        basket.addItem(CheckoutTestDataRepository.itemA());
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basket.getBasketItems().get(CheckoutTestDataRepository.itemA())).isEqualTo(1);
        assertThat(basket).isEqualTo(CheckoutTestDataRepository.aBasketWithItemA());


        basket.addItem(CheckoutTestDataRepository.itemA());
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basket.getBasketItems().get(CheckoutTestDataRepository.itemA())).isEqualTo(2);
        assertThat(basket).isEqualTo(CheckoutTestDataRepository.aBasketWithTwoItemA());

        basket.addItem(CheckoutTestDataRepository.itemA());
        assertThat(basket.getBasketItems().size()).isEqualTo(1);
        assertThat(basket.getBasketItems().get(CheckoutTestDataRepository.itemA())).isEqualTo(3);
        assertThat(basket).isEqualTo(CheckoutTestDataRepository.aBasketWithThreeItemA());
    }

    @Test
    public void verifyThatBasketKeepsBothItemsWhenSeparateItemsAreAdded() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);

        basket.addItem(CheckoutTestDataRepository.itemA());
        basket.addItem(CheckoutTestDataRepository.itemB());

        basket.addItem(CheckoutTestDataRepository.itemA());
        basket.addItem(CheckoutTestDataRepository.itemA());

        assertThat(basket.getBasketItems().size()).isEqualTo(2);
        assertThat(basket.getBasketItems().get(CheckoutTestDataRepository.itemB())).isEqualTo(1);
        assertThat(basket.getBasketItems().get(CheckoutTestDataRepository.itemA())).isEqualTo(3);
        assertThat(basket).isEqualTo(CheckoutTestDataRepository.aBasketWithThreeItemAandOneItemB());

    }

}