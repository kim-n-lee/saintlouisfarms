package org.liftoff.saintlouisfarms.models.DTO;

import org.liftoff.saintlouisfarms.models.BasketItem;
import org.liftoff.saintlouisfarms.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketDTO {
    private List<BasketItem> productsToBuy = new ArrayList<>();

    public ShoppingBasketDTO() {
    }

    public List<BasketItem> getProductsToBuy() {
        return productsToBuy;
    }

    public void setProductsToBuy(List<BasketItem> productsToBuy) {
        this.productsToBuy = productsToBuy;
    }
    public void addProductsToBuy(BasketItem basketItem){
        this.productsToBuy.add(basketItem);
    }
    public BasketItem getBasketItem(BasketItem basketItem){
        System.out.println(basketItem.getQuantity());
        return this.productsToBuy.get(productsToBuy.indexOf(basketItem));
    }
}

