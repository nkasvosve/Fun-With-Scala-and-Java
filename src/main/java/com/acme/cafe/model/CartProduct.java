package com.acme.cafe.model;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

/**
 * @author nick.kasvosve
 */
public class CartProduct {

    private int count;
    private Product product;
    private TemperatureType temperatureType;
    private BigDecimal total = BigDecimal.ZERO;

    /**
     * Initializes object and validates that product is valid and that temperatureType is valid
     *
     * @param product
     * @param count
     * @param temperatureType
     */
    public CartProduct(Product product, int count, TemperatureType temperatureType) {

        Validate.notNull(product, "product must not be null");
        Validate.notNull(temperatureType, "temperatureType must not be null");
        Validate.isTrue(count > 0, "count must be > 0");

        this.count = count;
        this.product = product;
        this.temperatureType = temperatureType;
        doTotal();
    }

    /**
     * Do not allow default construction in order to enforce invariants in public constructor
     */
    private CartProduct() {
    }

    private void doTotal() {
        total = product.getPrice().multiply(new BigDecimal(count));
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TemperatureType getTemperatureType() {
        return temperatureType;
    }

    public void setTemperatureType(TemperatureType temperatureType) {
        this.temperatureType = temperatureType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartProduct)) return false;

        CartProduct that = (CartProduct) o;

        if (count != that.count) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        if (temperatureType != that.temperatureType) return false;
        return total != null ? total.equals(that.total) : that.total == null;

    }

    @Override
    public int hashCode() {
        int result = count;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (temperatureType != null ? temperatureType.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CartProduct{");
        sb.append("count=").append(count);
        sb.append(", product=").append(product);
        sb.append(", temperatureType=").append(temperatureType);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
