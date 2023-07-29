package org.liftoff.saintlouisfarms.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class ShoppingBasket extends AbstractEntity {

    @OneToOne
    private Client client;

    @OneToMany(mappedBy = "shoppingBasket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> basketItems = new ArrayList<>();

    @OneToMany(mappedBy = "shoppingBasketAvailable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItem> basketItemsAvailable = new ArrayList<>();

    private BigDecimal totalAmount = BigDecimal.valueOf(0);

    private LocalDateTime localDateTime;
//
//    public ShoppingBasket(Client client, List<BasketItem> basketItems, BigDecimal totalAmount, LocalDateTime localDateTime) {
//        this.client = client;
//        this.basketItems = basketItems;
//        this.totalAmount = totalAmount;
//        this.localDateTime = localDateTime;
//    }
//
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



//    The controller indirectly uses this hanlder to add products to the shopping basket by calling
//    it on the ShoppingBasket instance obtained from the repository and passing the necessary arguments.
//    public void addProduct(Product product, int quantity) {
//        // Check if the product already exists
////        handler is used to get the first matching BasketItem if its exists
//        Optional<BasketItem> existingItem = basketItems.stream()
//                .filter(item -> item.getProduct().equals(product))
//                .findFirst();
//
//        if (existingItem.isPresent()) {
//            BasketItem item = existingItem.get();
//            item.setQuantity(item.getQuantity() + quantity);
//        } else {
//            BasketItem newItem = new BasketItem(product, quantity);
//            newItem.setProduct(product);
//            newItem.setQuantity(quantity);
//            newItem.setShoppingBasket(this);
//            basketItems.add(newItem);
//        }//. newItem.setShoppingBasket(this);By setting this as the shopping basket for the new item, the new item becomes part of the shopping basket.
//    }
    public void addProduct(BasketItem basketItem) {
        this.basketItems.add(basketItem);
    }


    public void addProductsToBuy(BasketItem basketItem){
        this.basketItemsAvailable.add(basketItem);
    }

    public void removeProduct(Product product) {
        basketItems.removeIf(item -> item.getProduct().equals(product));
    }// lambda expression checks if the product object of basketItem in the list is the same object we want to remove

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public BasketItem getBasketItem(BasketItem basketItem){
        return this.basketItemsAvailable.get(basketItemsAvailable.indexOf(basketItem));
    }

    public List<BasketItem> getBasketItemsAvailable() {
        return basketItemsAvailable;
    }

    public void setBasketItemsAvailable(List<BasketItem> basketItemsAvailable) {
        this.basketItemsAvailable = basketItemsAvailable;
    }


}




