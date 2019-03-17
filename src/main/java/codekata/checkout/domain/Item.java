package codekata.checkout.domain;

import java.util.Objects;

/**
 * Domain Object to represent the item.
 */
public class Item implements Comparable<Item> {
    private final String skuCode;
    private final Double price;

    public Item(final String skuCode, final Double price) {
        this.skuCode = skuCode;
        this.price = price;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(skuCode, item.skuCode) &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuCode, price);
    }

    @Override
    public String toString() {
        return "Item{" + "skuCode='" + skuCode + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Item other) {
        return (this.skuCode.compareTo(other.skuCode));
    }
}
