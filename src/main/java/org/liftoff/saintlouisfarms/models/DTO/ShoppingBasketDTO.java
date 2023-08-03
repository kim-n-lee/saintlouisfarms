package org.liftoff.saintlouisfarms.models.DTO;

import org.liftoff.saintlouisfarms.models.BasketItem;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketDTO {
    private List<BasketItem> basketItemsAvailable = new ArrayList<>();

    public ShoppingBasketDTO() {
    }



        public BasketItem getBasketItem(BasketItem basketItem){
        return this.basketItemsAvailable.get(basketItemsAvailable.indexOf(basketItem));
    }

    public void addBasketItem(BasketItem basketItem){
        basketItemsAvailable.add(basketItem);
    }

    public List<BasketItem> getBasketItemsAvailable() {
        return basketItemsAvailable;
    }

    public void setBasketItemsAvailable(List<BasketItem> basketItemsAvailable) {
        this.basketItemsAvailable = basketItemsAvailable;
    }



}
