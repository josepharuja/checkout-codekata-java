package codekata.checkout.domain;

import codekata.checkout.service.BasketState;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Simple Basket to hold the checkout transaction
 */
public class Basket {

    private final TreeMap<Item, Integer> basketItems;

    private BasketState basketState;

    private Double total = 0.0;

    public Basket(final BasketState basketState) {
        this.basketState = basketState;
        basketItems = new TreeMap<>();
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(final Double total) {
        this.total = total;
    }

    public void addItem(final Item item) {
        if (!basketItems.containsKey(item))
            basketItems.put(item, 1);
        else {
            basketItems.put(item, 1 + basketItems.get(item));
        }
    }

    public SortedMap<Item, Integer> getBasketItems() {
        return basketItems;
    }

    public BasketState getBasketState() {
        return basketState;
    }

    public void setBasketState(BasketState basketState) {
        this.basketState = basketState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(basketItems, basket.basketItems) &&
                Objects.equals(total, basket.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketItems, total);
    }

    @Override
    public String toString() {
        return "Basket{" + "basketItems=" + basketItems +
                ", basketState=" + basketState +
                ", total=" + total +
                '}';
    }
}
