package com.matveyeu.shop.domain;

import java.io.Serializable;

import com.matveyeu.shop.domain.currency.Currency;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    @NotNull
    @NotEmpty
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;

    @Column(name = "description",length = 500)
    @NotNull
    @NotEmpty
    private String description;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "currency")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency usedCurrency;
}
