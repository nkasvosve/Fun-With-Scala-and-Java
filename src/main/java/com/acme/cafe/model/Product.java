package com.acme.cafe.model;

import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;

/**
 * @author nick.kasvosve
 *
 */
public class Product {

    private String name;
    private BigDecimal price;
    private String description;
    private ProductType productType;

    public Product(String name, BigDecimal price, String description, ProductType productType) {

        Validate.notBlank(name, "name must not be null");
        Validate.notNull(price, "price must not be null");
        Validate.notBlank(description, "description must not be null");
        Validate.notNull(productType, "productType must not be null");

        this.name = name;
        this.price = price;
        this.description = description;
        this.productType = productType;
    }

    private Product(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        return productType == product.productType;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", description='").append(description).append('\'');
        sb.append(", productType=").append(productType);
        sb.append('}');
        return sb.toString();
    }
}
