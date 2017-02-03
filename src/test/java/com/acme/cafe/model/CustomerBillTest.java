package com.acme.cafe.model;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @author nick.kasvosve
 */
public class CustomerBillTest {

    private CustomerBill customerBill;

    @Before
    public void setup() {
        customerBill = new CustomerBill();
    }

    @Test(expected = NullPointerException.class)
    public void showNullCartProductCannotBeAddedToCart() {
        customerBill.addCartProduct(null);
    }

    @Test(expected = NullPointerException.class)
    public void showZeroCountProductCannotBeAddedToCart() {
        customerBill.addCartProduct(new CartProduct(null, 1, TemperatureType.COLD));
    }

    @Test(expected = NullPointerException.class)
    public void showNullCartProductProductTypeCannotBeAddedToCart() {
        customerBill.addCartProduct(new CartProduct(
                new Product("Coffer", new BigDecimal("1.00"), "Coffee", null), 1,
                TemperatureType.COLD));
    }

    @Test
    public void showAddToCartComputesTotals() {

        BigDecimal price = new BigDecimal("1.20");

        customerBill.addCartProduct(new CartProduct(
                new Product("Coffer", price, "Cold Coffee", ProductType.DRINK), 2,
                TemperatureType.COLD));

        BigDecimal currentTotal = price.multiply(new BigDecimal(2));
        org.junit.Assert.assertTrue(currentTotal.equals(new BigDecimal("2.40")));

        org.junit.Assert.assertTrue(customerBill.getTotal().equals((currentTotal)));

        price = new BigDecimal("2.30");
        currentTotal = currentTotal.add(price.multiply(new BigDecimal(2)));
        org.junit.Assert.assertTrue(currentTotal.equals(new BigDecimal("7.00")));  //2.40 + 4.60 = 7.0

        customerBill.addCartProduct(new CartProduct(
                new Product("Sandwich", price, "Hot Sandwich", ProductType.FOOD), 2,
                TemperatureType.HOT)); // 7.0 x .20 = 8.40

        org.junit.Assert.assertTrue(customerBill.getTotal().equals((new BigDecimal("8.40"))));

        printReceipt(customerBill);
    }

    Random random = new Random(1);

    private void printReceipt(CustomerBill customerBill) {
        VelocityEngine ve = new VelocityEngine();
        Properties props = new Properties();
        props.put("file.resource.loader.path", "src/test/resources");
        ve.init(props);
        Template t = ve.getTemplate( "receipt-template.vtl" );

        VelocityContext context = new VelocityContext();
        context.put("date", new Date());
        context.put("server", "John");
        context.put("table",  1 + random.nextInt(10));
        context.put("checkNumber", 1 + random.nextInt(10));
        context.put("customerBill", customerBill);

        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        System.out.println( writer.toString() );
    }


    public static Product buildProduct(BigDecimal price) {
        return new Product("Coffee", price, "Hot Coffee", ProductType.DRINK);
    }
}
