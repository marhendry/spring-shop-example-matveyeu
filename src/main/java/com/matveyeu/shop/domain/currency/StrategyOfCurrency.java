package com.matveyeu.shop.domain.currency;

import com.matveyeu.shop.domain.Product;

public interface StrategyOfCurrency {
    Product recountPrice (Product product);
}
