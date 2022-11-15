package com.matveyeu.shop.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user")
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", unique = true)
    @NotEmpty
    @NotNull
    @Size(min = 4, max = 32)
    private String username;

    @Column(name = "email", unique = true)
    @Email
    @NotEmpty
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 8, max = 32)
    private String password;

    @NotEmpty
    @NotNull
    private String passwordConfirm;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    @Min(13)
    private int age;

    @Column(name = "city")
    private String city;

    @Column(name = "gender")
    @NotEmpty
    @NotNull
    private String gender;

    @Column(name = "balance")
    private BigDecimal balance;

}