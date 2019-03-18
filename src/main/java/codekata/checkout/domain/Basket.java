package codekata.checkout.domain;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Simple Basket to hold the checkout transaction
 */
//TODO : Basket does not currently list the promotions applied as items in th basket.
//TODO : This is what generally we could see in the supermarket receipt.
//TODO : Which generally list all the items and then list the promotions for the items with negative amount
//TODO : This can be easily achieved during price calculation by returning an Object type from PriceCalculator
//TODO : And basket can then store the list of promotions applied for the items

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
