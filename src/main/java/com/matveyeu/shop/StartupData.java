package com.matveyeu.shop;

import com.matveyeu.shop.domain.Category;
import com.matveyeu.shop.domain.Product;
import com.matveyeu.shop.domain.User;
import com.matveyeu.shop.repository.CategoryRepository;
import com.matveyeu.shop.service.ProductService;
import com.matveyeu.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class StartupData implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(StartupData.class);

       @Override
    public void run(String... args) {
        createInitialAdminAccount();
        createInitialUserAccount();
        createInitialCategory();
        createInitialExampleProducts();
    }

    private void createInitialUserAccount(){
        User user = new User();

        user.setUsername("user");
        user.setPassword("user");
        user.setPasswordConfirm("user");
        user.setGender("Female");
        user.setEmail("user@example.com");

        userService.save(user);
    }

    private void createInitialAdminAccount(){
        User admin = new User();

        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setPasswordConfirm("admin");
        admin.setGender("Male");
        admin.setEmail("admin@example.com");

        userService.save(admin);
    }

    private void createInitialCategory(){
        Category category1 = new Category();
        Category category2 = new Category();
        category1.setId(1);
        category1.setCategoryName("Food");
        category2.setId(2);
        category2.setCategoryName("Not Food");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    private void createInitialExampleProducts(){
        final String NAME = "Example Name";
        final String IMAGE_URL = "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX7389458.jpg";
        final String DESCRIPTION = "Example Description";
        final BigDecimal PRICE = BigDecimal.valueOf(22);

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        product1.setName(NAME);
        product1.setImageUrl(IMAGE_URL);
        product1.setDescription(DESCRIPTION);
        product1.setCategory(categoryRepository.findByCategoryName("Food"));
        product1.setPrice(PRICE);

        product2.setName(NAME);
        product2.setImageUrl(IMAGE_URL);
        product2.setDescription(DESCRIPTION);
        product2.setCategory(categoryRepository.findByCategoryName("Food"));
        product2.setPrice(PRICE);

        product3.setName(NAME);
        product3.setImageUrl(IMAGE_URL);
        product3.setDescription(DESCRIPTION);
        product3.setCategory(categoryRepository.findByCategoryName("Not Food"));
        product3.setPrice(PRICE);

        product4.setName(NAME);
        product4.setImageUrl(IMAGE_URL);
        product4.setDescription(DESCRIPTION);
        product4.setCategory(categoryRepository.findByCategoryName("Not Food"));
        product4.setPrice(PRICE);

        productService.save(product1);
        productService.save(product2);
        productService.save(product3);
        productService.save(product4);
    }
}