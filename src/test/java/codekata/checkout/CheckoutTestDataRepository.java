package codekata.checkout;

import codekata.checkout.domain.Basket;
import codekata.checkout.domain.Item;
import codekata.checkout.domain.Promotion;
import codekata.checkout.domain.BasketState;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * The Test data repository for CheckOut for {@link Item}, {@link Promotion} and {@link Basket}
 */
public class CheckoutTestDataRepository {

    public static Item itemA() {
        return new Item("A", 50.0);
    }

    public static Item itemB() {
        return new Item("B", 30.0);
    }

    public static Item itemC() {
        return new Item("C", 20.0);
    }

    public static Item itemD() {
        return new Item("D", 15.0);
    }

    public static List<Promotion> getPromotionsForThisWeek() {
        return Arrays.asList(getItemAPromotion(), getItemBPromotion());
    }

    private static Promotion getItemBPromotion() {

        return new Promotion(itemB(), 2, 45.0, LocalDateTime.now());
    }

    private static Promotion getItemAPromotion() {
        return new Promotion(itemA(), 3, 130.0, LocalDateTime.now());
    }

    public static String anUUID() {
        return UUID.randomUUID().toString();
    }

    public static Basket anEmptyBasket() {
        return new Basket(BasketState.IN_PROGRESS);
    }

    public static Basket aBasketWithItemAWithTotal() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        basket.setTotal(50.0);
        return basket;
    }

    public static Basket aBasketWithTwoItemAWithTotal() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.setTotal(100.0);
        return basket;
    }

    public static Basket aBasketWithThreeItemAWithTotal() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.setTotal(130.0);
        return basket;
    }


    public static Basket aBasketWithItemA() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        return basket;
    }

    public static Basket aBasketWithTwoItemA() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        basket.addItem(itemA());
        return basket;
    }

    public static Basket aBasketWithThreeItemA() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemA());
        return basket;
    }


    public static Basket aBasketWithThreeItemAandOneItemB() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemB());
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemA());
        return basket;
    }


    public static Basket aBasketWithThreeItemAandTwoItemBWithTotal() {
        Basket basket = new Basket(BasketState.IN_PROGRESS);
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemA());
        basket.addItem(itemB());
        basket.addItem(itemB());
        basket.setTotal(175.0);
        return basket;
    }
}
