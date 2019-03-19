package codekata.checkout.service;

import codekata.checkout.domain.AppliedDiscount;
import codekata.checkout.domain.Item;
import codekata.checkout.domain.Promotion;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class PriceCalculator {


    public TreeMap<Promotion, AppliedDiscount> calculateDiscounts(SortedMap<Item, Integer> basketItems, List<Promotion> currentPromotions) {

        TreeMap<Promotion, AppliedDiscount> appliedDiscountTreeMap = new TreeMap<>();
        for (Promotion promotion : currentPromotions) {
            final int promotionQuantity = promotion.getQuantity();
            final double promotionPrice = promotion.getPrice();
            if (basketItems.containsKey(promotion.getItem())) {
                final Integer itemQuantity = basketItems.get(promotion.getItem());
                if (itemQuantity >= promotionQuantity) {
                    final Double itemPrice = promotion.getItem().getPrice();
                    if (itemQuantity == promotionQuantity) {
                        appliedDiscountTreeMap.put(promotion, new AppliedDiscount(promotionQuantity, (itemQuantity * itemPrice) - promotionPrice));
                    } else {
                        final double totalItemPriceInPromotion = (itemQuantity * itemPrice) - ((itemQuantity % promotionQuantity) * itemPrice);
                        final double totalPromotionPrice = (itemQuantity / promotionQuantity) * promotionPrice;
                        final double discount = totalItemPriceInPromotion - totalPromotionPrice;
                        appliedDiscountTreeMap.put(promotion, new AppliedDiscount(promotionQuantity, discount));
                    }
                }
            }

        }
        return appliedDiscountTreeMap;
    }
}
