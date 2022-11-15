package com.matveyeu.shop.validator;

import com.matveyeu.shop.domain.Category;

import java.util.List;

public interface CategoryService {

    void save(Category category);
    List<Category> findAll();
}