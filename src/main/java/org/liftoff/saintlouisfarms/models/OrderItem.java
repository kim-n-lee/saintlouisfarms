package org.liftoff.saintlouisfarms.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity

public class OrderItem extends AbstractEntity {

    @ManyToOne
    private Product product;
    private Integer quantity;

    @ManyToOne
    private FarmOrder orderItem;

    public OrderItem(BasketItem basketItem) {
        this.product = basketItem.getProduct();
        this.quantity = basketItem.getQuantity();
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem(BasketItem basketItem, FarmOrder farmOrder) {
        this.product = basketItem.getProduct();
        this.quantity = basketItem.getQuantity();
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
}
