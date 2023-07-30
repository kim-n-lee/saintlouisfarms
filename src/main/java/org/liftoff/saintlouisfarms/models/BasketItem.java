package org.liftoff.saintlouisfarms.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class BasketItem extends AbstractEntity {

    @ManyToOne
    private Product product;
    private Integer quantity;
    @ManyToMany(mappedBy = "basketItems", cascade = CascadeType.ALL)
    private List<ShoppingBasket> shoppingBasket=new ArrayList<>();
    @ManyToMany(mappedBy = "basketItemsAvailable", cascade = CascadeType.ALL)
    private List<ShoppingBasket> shoppingBasketAvailable=new ArrayList<>();
    @ManyToOne
    private FarmOrder farmOrderItem;

    public BasketItem(Product product, int quantity, ShoppingBasket shoppingBasket) {
        this.product = product;
        this.quantity = quantity;
        this.shoppingBasket.add(shoppingBasket);
    }

    public BasketItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BasketItem() {
    }

    public Product getProduct() {
        return product;
    }

    public List<ShoppingBasket> getShoppingBasket() {
        return shoppingBasket;
    }

    public void setShoppingBasket(ShoppingBasket shoppingBasket) {
        this.shoppingBasket.add(shoppingBasket);
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public List<ShoppingBasket> getShoppingBasketAvailable() {
        return shoppingBasketAvailable;
    }

    public FarmOrder getFarmOrderItem() {
        return farmOrderItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setShoppingBasketAvailable(ShoppingBasket shoppingBasketAvailable) {
        this.shoppingBasketAvailable.add(shoppingBasketAvailable);
    }

    public FarmOrder getOrderItem() {
        return farmOrderItem;
    }

    public void setOrderItem(FarmOrder farmOrderItem) {
        this.farmOrderItem = farmOrderItem;
    }
}
