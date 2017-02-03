package com.acme.cafe.model;


import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the items the customer is to be charged for
 */
public class CustomerBill {

    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal totalDiscount = BigDecimal.ZERO;
    private BigDecimal serviceCharge = BigDecimal.ZERO;

    private List<CartProduct> cartProducts = new ArrayList<CartProduct>();


    /**
     * Add product to cart and enforces invariants such as price, temperatureType and total
     *
     * @param cartProduct
     */
    public void addCartProduct(CartProduct cartProduct) {

        Validate.notNull(cartProduct, "cartProduct must not be null");

        cartProducts.add(cartProduct);
        doTotals();
        total = total.setScale(2, RoundingMode.CEILING);
        subTotal = subTotal.setScale(2, RoundingMode.CEILING);
        serviceCharge = serviceCharge.setScale(2, RoundingMode.CEILING);
    }

    /**
     * @param cartProducts
     */
    public void addCartProducts(List<CartProduct> cartProducts) {
        for (CartProduct cartProduct : cartProducts) {
            addCartProduct(cartProduct);
        }
    }

    private synchronized void doTotals() {
        total = BigDecimal.ZERO;
        boolean hasHotFoodItems = false;
        boolean hasFoodItems = false;

        for (CartProduct cartProduct : cartProducts) {
            total = total.add(cartProduct.getTotal());

            if (cartProduct.getProduct().getProductType() == ProductType.FOOD) {
                hasFoodItems = true;
                if (cartProduct.getTemperatureType() == TemperatureType.HOT) {
                    hasHotFoodItems = true;
                }
            }
        }
        subTotal = total;

        if (!hasFoodItems) { //When all purchased items are drinks no service charge is applied
            return;
        }

        BigDecimal servicePercentage = null;
        BigDecimal maxServiceCharge = new BigDecimal("20.00");

        if (hasHotFoodItems) { //When purchased items include any hot food apply a service charge of 20% to the total bill with a maximum £20 service charge
            servicePercentage = new BigDecimal("0.20");
            serviceCharge = total.multiply(servicePercentage);
            if (serviceCharge.compareTo(maxServiceCharge) > 0) {
                serviceCharge = maxServiceCharge;
            }
            total = total.add(serviceCharge);
            return;
        }

        if (hasFoodItems) { //When purchased items include any food apply a service charge of 10%
            servicePercentage = new BigDecimal("0.10");
            serviceCharge = total.multiply(servicePercentage);
            total = total.add(serviceCharge);
            return;
        }
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerBill)) return false;

        CustomerBill that = (CustomerBill) o;

        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (subTotal != null ? !subTotal.equals(that.subTotal) : that.subTotal != null) return false;
        if (totalDiscount != null ? !totalDiscount.equals(that.totalDiscount) : that.totalDiscount != null)
            return false;
        return cartProducts != null ? cartProducts.equals(that.cartProducts) : that.cartProducts == null;

    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    @Override
    public int hashCode() {
        int result = total != null ? total.hashCode() : 0;
        result = 31 * result + (subTotal != null ? subTotal.hashCode() : 0);
        result = 31 * result + (totalDiscount != null ? totalDiscount.hashCode() : 0);
        result = 31 * result + (cartProducts != null ? cartProducts.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerBill{");
        sb.append("total=").append(total);
        sb.append(", subTotal=").append(subTotal);
        sb.append(", totalDiscount=").append(totalDiscount);
        sb.append(", cartProducts=").append(cartProducts);
        sb.append('}');
        return sb.toString();
    }

}