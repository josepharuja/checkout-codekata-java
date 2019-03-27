package codekata.checkout.domain;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Simple Basket to hold the checkout transaction
 */
public class Basket {

    private final TreeMap<Item, Integer> basketItems;

    private TreeMap<Promotion, AppliedDiscount> appliedDiscounts;

    private BasketState basketState;

    public Basket(final BasketState basketState) {
        this.basketState = basketState;
        basketItems = new TreeMap<>();
        appliedDiscounts = new TreeMap<>();
    }

    public Double getTotal() {
        Double sum = basketItems.entrySet().stream()
                .map(x -> x.getKey().getPrice() * x.getValue()).mapToDouble(Double::doubleValue).sum();

        Double discount = appliedDiscounts.values().stream()
                .map(AppliedDiscount::getDiscount).mapToDouble(Double::doubleValue).sum();
        return sum - discount;
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

    public void setAppliedDiscounts(TreeMap<Promotion, AppliedDiscount> appliedDiscounts) {
        this.appliedDiscounts = appliedDiscounts;
    }

    public SortedMap<Promotion, AppliedDiscount> getAppliedDiscounts() {
        return appliedDiscounts;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Basket{");
        sb.append("basketItems=").append(basketItems);
        sb.append(", basketState=").append(basketState);
        sb.append(", total=").append(getTotal());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(basketItems, basket.basketItems) &&
                basketState == basket.basketState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(basketItems, basketState);
    }
}
