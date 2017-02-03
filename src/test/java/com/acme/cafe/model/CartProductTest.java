package com.acme.cafe.model;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author nick.kasvosve
 */
public class CartProductTest {

    @Test(expected = NullPointerException.class)
    public void showNullProductIsRejected() {
        new CartProduct(null, 1 , TemperatureType.COLD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showZeroCountIsRejected() {
        BigDecimal price = new BigDecimal("1.20");
        new CartProduct(CustomerBillTest.buildProduct(price), -1 , TemperatureType.COLD);
    }

    @Test(expected = NullPointerException.class)
    public void showNullTemperatureTypeIsRejected() {
        BigDecimal price = new BigDecimal("1.20");
        new CartProduct(CustomerBillTest.buildProduct(price), -1 , null);
    }

    @Test
    public void showValidProductIsAddedTocart() {
        BigDecimal price = new BigDecimal("1.20");
        BigDecimal currentTotal = price.multiply(new BigDecimal(2));
        CartProduct cartProduct = new CartProduct(CustomerBillTest.buildProduct(price), 2 ,  TemperatureType.HOT);
        org.junit.Assert.assertNotNull(cartProduct);
        org.junit.Assert.assertTrue(cartProduct.getTotal().equals((currentTotal)));
        org.junit.Assert.assertTrue(cartProduct.getProduct().getPrice().equals((price)));
    }
}
