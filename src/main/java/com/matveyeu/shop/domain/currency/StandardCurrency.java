package com.matveyeu.shop.domain.currency;

import com.matveyeu.shop.domain.Product;

import java.math.BigDecimal;

public class StandardCurrency implements StrategyOfCurrency {
    static final BigDecimal coefficientStandard = new BigDecimal(0.2);

    @Override
    public Product recountPrice(Product product) {
        BigDecimal price = product.getPrice();
        product.setPrice(price.add((price.multiply(coefficientStandard))));
        return product;
    }
}
