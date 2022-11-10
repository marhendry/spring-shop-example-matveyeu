package com.matveyeu.shop.domain.currency;

import java.math.BigDecimal;

public enum Currency {
    PLN(985, BigDecimal.valueOf(1)),
    USD(840, BigDecimal.valueOf(0.0271));
    private final int internationalCode;
    private BigDecimal course;
    Currency(int internationalCode, BigDecimal course) {
        this.internationalCode = internationalCode;
        this.course = course;
    }
    public int getInternationalCode() {
        return internationalCode;
    }
    public BigDecimal getCourse() {
        return course;
    }
    public void setCourse(BigDecimal course) {
        this.course = course;
    }
    }
