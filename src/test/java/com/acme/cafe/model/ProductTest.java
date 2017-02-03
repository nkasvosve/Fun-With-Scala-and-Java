package com.acme.cafe.model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author nick.kasvosve
 */
public class ProductTest {

    private CustomerBill customerBill;

    @Before
    public void setup() {
        customerBill = new CustomerBill();
    }

    @Test(expected = NullPointerException.class)
    public void showNullProductTypeIsRejected() {
        new Product("Coffer", new BigDecimal("1.00"), "Coffee", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showEmptyDescriptionIsRejected() {
        new Product("Coffer", new BigDecimal("1.00"), "", ProductType.DRINK);
    }

    @Test(expected = NullPointerException.class)
    public void showNullPriceIsRejected() {
        new Product("Coffer", null, "Coffee", ProductType.DRINK);
    }

    @Test(expected = IllegalArgumentException.class)
    public void showEmptyNameIsRejected() {
        new Product("", new BigDecimal("1.00"), "Coffee", ProductType.DRINK);
    }

    @Test
    public void showValidProducthasValidFiels() {
        BigDecimal price = new BigDecimal("1.20");
        Product product = CustomerBillTest.buildProduct(price);
        org.junit.Assert.assertTrue(product.getPrice().equals((price)));
        org.junit.Assert.assertEquals(product.getName(), "Coffee");
        org.junit.Assert.assertEquals(product.getDescription(), "Hot Coffee");
        org.junit.Assert.assertEquals(product.getProductType(), ProductType.DRINK);
    }
}
