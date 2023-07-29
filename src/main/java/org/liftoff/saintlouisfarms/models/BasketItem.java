package org.liftoff.saintlouisfarms.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity

public class BasketItem extends AbstractEntity {

    @ManyToOne
    private Product product;

    private Integer quantity;
    @ManyToOne
    private ShoppingBasket shoppingBasket;

    @ManyToOne
    private ShoppingBasket shoppingBasketAvailable;

    public BasketItem(Product product, int quantity, ShoppingBasket shoppingBasket) {
        this.product = product;
        this.quantity = quantity;
        this.shoppingBasket = shoppingBasket;
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

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public void setShoppingBasket(ShoppingBasket shoppingBasket) {
        this.shoppingBasket = shoppingBasket;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public ShoppingBasket getShoppingBasketAvailable() {
        return shoppingBasketAvailable;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setShoppingBasketAvailable(ShoppingBasket shoppingBasketAvailable) {
        this.shoppingBasketAvailable = shoppingBasketAvailable;
    }
}
