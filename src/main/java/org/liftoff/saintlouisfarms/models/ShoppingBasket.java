package org.liftoff.saintlouisfarms.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
public class ShoppingBasket extends AbstractEntity{


    @OneToOne()
    private  Client client;
    private int Quantity;
    @ManyToOne
    private Product products;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }



    public Client getClient() {
        return client;
    }


    public void setClient(Client client) {
        this.client = client;
    }


    private LocalDateTime localDateTime;
   // private double totalAmount;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

//    public double getTotalAmount() {
//        return totalAmount;
//    }
//
//    public void setTotalAmount(double totalAmount) {
//
//        this.totalAmount = totalAmount;
//    }
    public ShoppingBasket(){

    }

    public ShoppingBasket(Client client, int quantity, LocalDateTime localDateTime,Product product) {
        this.client = client;
        Quantity = quantity;
        this.localDateTime = localDateTime;
        this.products=product;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }
}
