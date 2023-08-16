package org.liftoff.saintlouisfarms.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity

public class OrderItem extends AbstractEntity {

    @ManyToOne
    private Product product;
    private Integer quantity;
    private String name;
    private String measurement;
    private BigDecimal price;

    @ManyToOne
    private FarmOrder orderItem;

    public OrderItem(BasketItem basketItem) {
        this.product = basketItem.getProduct();
        this.quantity = basketItem.getQuantity();
        this.name = basketItem.getProduct().getName();
        this.measurement = basketItem.getProduct().getMeasurementCategory().getName();
        this.price = basketItem.getProduct().getProductDetails().getPrice();
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem(BasketItem basketItem, FarmOrder farmOrder) {
        this.product = basketItem.getProduct();
        this.quantity = basketItem.getQuantity();
        this.name = basketItem.getProduct().getName();
        this.measurement = basketItem.getProduct().getMeasurementCategory().getName();
        this.price = basketItem.getProduct().getProductDetails().getPrice();
        this.orderItem = farmOrder;
    }


    public OrderItem(FarmOrder orderItem) {
        this.orderItem = orderItem;
    }

    public OrderItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public FarmOrder getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(FarmOrder orderItem) {
        this.orderItem = orderItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
