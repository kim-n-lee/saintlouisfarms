package org.liftoff.saintlouisfarms.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class ShoppingBasket extends AbstractEntity {

    @ManyToOne()
    private Client client;

    @OneToMany
    private List<BasketItem> basketItems = new ArrayList<>();

    private BigDecimal totalAmount = BigDecimal.valueOf(0);

    private LocalDateTime localDateTime;

    public ShoppingBasket(Client client, LocalDateTime localDateTime) {
        this.client = client;
        this.localDateTime = localDateTime;
    }

    public ShoppingBasket() {
    }


    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    public void setBasketItems(List<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }



    //
    public void addProduct(BasketItem basketItem) {
        if (basketItems.contains(basketItem)) {
            basketItems.get(basketItems.indexOf(basketItem)).setQuantity(basketItem.getQuantity());
        } else {
            this.basketItems.add(basketItem);
        }
    }


    public void removeProduct(Product product) {
        basketItems.removeIf(item -> item.getProduct().equals(product));
    }// lambda expression checks if the product object of basketItem in the list is the same object we want to remove

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }




}