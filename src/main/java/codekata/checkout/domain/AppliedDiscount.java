package codekata.checkout.domain;

import java.util.Objects;

/**
 * Domain to store the applied promotion discounts in the {@link Basket}
 */
public class AppliedDiscount {
    private final Integer quantity;
    private final double  discount;

    public AppliedDiscount(Integer quantity, double discount) {
        this.quantity = quantity;
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppliedDiscount that = (AppliedDiscount) o;
        return Double.compare(that.discount, discount) == 0 &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, discount);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AppliedDiscount{");
        sb.append("quantity=").append(quantity);
        sb.append(", discount=").append(discount);
        sb.append('}');
        return sb.toString();
    }
}
