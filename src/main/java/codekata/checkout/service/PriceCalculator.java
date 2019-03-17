package codekata.checkout.service;

import codekata.checkout.domain.Item;
import codekata.checkout.domain.Promotion;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.function.Predicate;

public class PriceCalculator {

    public Double calculateTotal(SortedMap<Item, Integer> basketItems, List<Promotion> currentPromotions) {

        Double total = 0.0;
        for (Item item : basketItems.keySet()) {
            int itemQuantity = basketItems.get(item);
            //TODO : Extract predicate to a constant
            final Predicate<Promotion> promotionPredicate = promotion -> promotion.getItem().equals(item);
            final Optional<Promotion> promotionOptional = currentPromotions.stream().filter(promotionPredicate).findFirst();

            final Double itemPrice = item.getPrice();
            if (promotionOptional.isPresent()) {
                Promotion promotion = promotionOptional.get();
                final int promotionQuantity = promotionOptional.get().getQuantity();
                final Double promotionPrice = promotion.getPrice();
                //TODO : Evaluate expresssion using a functional interface
                if (promotionQuantity >= itemQuantity) {
                    if (promotionQuantity == itemQuantity) {
                        total += promotionPrice;
                    } else {
                        total += (itemQuantity) * itemPrice;
                    }
                } else {
                    total += ((itemQuantity % promotionQuantity) * itemPrice) + ((itemQuantity / promotionQuantity) * promotionPrice);
                }
            } else {
                total += (itemQuantity) * itemPrice;
            }
        }

        return total;
    }

}
