package codekata.checkout.service;

import codekata.checkout.domain.Basket;
import codekata.checkout.domain.BasketState;
import codekata.checkout.domain.Item;
import codekata.checkout.domain.Promotion;

import java.util.*;

/**
 * The Service to manage the Checkout transaction
 *
 * @see Promotion
 * @see Basket
 */
public class CheckoutService {

    private final UUIDSupplier uuidSupplier;

    private final PriceCalculator priceCalculator;

    private final List<Promotion> currentPromotions = new ArrayList<>();

    private final Map<String, Basket> basketMap = new HashMap<>();

    public CheckoutService(final UUIDSupplier uuidSupplier, final PriceCalculator priceCalculator) {
        this.uuidSupplier = uuidSupplier;
        this.priceCalculator = priceCalculator;
    }


    public String startTransaction(List<Promotion> promotions) {
        this.currentPromotions.clear();
        if (null != promotions && !promotions.isEmpty()) {
            this.currentPromotions.addAll(promotions);
        }
        final String basketId = uuidSupplier.get();
        basketMap.put(basketId, new Basket(BasketState.IN_PROGRESS));
        return basketId;
    }

    public Basket getBasket(String checkOutId) {
        return basketMap.get(checkOutId);
    }

    public List<Promotion> getPromotions() {
        return currentPromotions;
    }

    public Basket scanItem(String basketUUID, Item item) {
        final Basket basket = basketMap.get(basketUUID);
        basket.addItem(item);
        final SortedMap<Item, Integer> basketItems = basket.getBasketItems();
        basket.setTotal(priceCalculator.calculateTotal(basketItems, currentPromotions));
        return basket;
    }

    public Basket completeCheckOut(String basketUUID) {
        final Basket basket = basketMap.get(basketUUID);
        basket.setBasketState(BasketState.COMPLETE);
        return basket;
    }

    public Map<String, Basket> getBasketMap() {
        return basketMap;
    }
}
