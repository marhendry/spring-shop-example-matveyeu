package com.matveyeu.shop.service.impl;

import com.matveyeu.shop.domain.Product;
import com.matveyeu.shop.service.ShoppingCartService;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private Map<Product, Integer> cart;

    @PostConstruct
    public void init() {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("map.ser");
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                cart = (Map) ois.readObject();
            } catch (Exception e) {
                cart = new LinkedHashMap<>();
            }
        } catch (Exception e) {
            cart = new LinkedHashMap<>();
        }

    }

    @Override
    public void addProduct(Product product) {
        if (cart.containsKey(product)){
            cart.replace(product, cart.get(product) + 1);
        }else{
            cart.put(product, 1);
        }
        updateCartFile();
    }

    @Override
    public void removeProduct(Product product) {
        if (cart.containsKey(product)) {
            if (cart.get(product) > 1)
                cart.replace(product, cart.get(product) - 1);
            else if (cart.get(product) == 1) {
                cart.remove(product);
            }
        }
        updateCartFile();
    }

    @Override
    public void clearProducts() {
        cart.clear();
        updateCartFile();
    }

    @Override
    public Map<Product, Integer> productsInCart() {
        return Collections.unmodifiableMap(cart)
                ;
    }

    @Override
    public BigDecimal totalPrice() {
        return cart.entrySet().stream()
                .map(k -> k.getKey().getPrice().multiply(BigDecimal.valueOf(k.getValue()))).sorted()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public void cartCheckout() {
        cart.clear();
        updateCartFile();
        // Normally there would be payment etc.
    }

    public void updateCartFile() {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream("map.ser");
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cart)
            ;
            objectOutputStream.close();
        } catch (IOException ioException) {
            log.error("Could not serialize cart map");
        }
    }
}