package codekata.checkout.domain;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain Object to represent the item Promotion
 *
 * @see Item
 */
public class Promotion implements Comparable<Promotion> {

    private final Item item;

    private final int quantity;

    private final Double price;

    private final LocalDateTime activeFrom;

    public Promotion(final Item item, final int quantity, final Double price, final LocalDateTime activeFrom) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.activeFrom = activeFrom;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getActiveFrom() {
        return activeFrom;
    }


    @Override
    public String toString() {
        return "Promotion{" + "item=" + item +
                ", quantity=" + quantity +
                ", price=" + price +
                ", activeFrom=" + activeFrom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return Objects.equals(item, promotion.item) &&
                Objects.equals(quantity, promotion.quantity) &&
                Objects.equals(price, promotion.price) &&
                Objects.equals(activeFrom, promotion.activeFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item, quantity, price, activeFrom);
    }

    @Override
    public int compareTo(@NotNull Promotion o) {
        return (this.price.compareTo(o.price));
    }
}
