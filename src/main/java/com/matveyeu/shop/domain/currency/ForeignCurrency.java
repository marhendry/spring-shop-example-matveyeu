package com.matveyeu.shop.domain.currency;

import com.matveyeu.shop.domain.Product;

import java.math.BigDecimal;

public class ForeignCurrency implements StrategyOfCurrency {
    BigDecimal coefficientForeighn = new BigDecimal(0.25);

    @Override
    public Product recountPrice(Product product) {
        BigDecimal price = product.getPrice().divide(product.getUsedCurrency().getCourse());
        price = price.divide(product.getPrice());
        product.setPrice(price.add((price.multiply(coefficientForeighn))));
        product.setUsedCurrency(Currency.PLN);
        return product;
    }
}
