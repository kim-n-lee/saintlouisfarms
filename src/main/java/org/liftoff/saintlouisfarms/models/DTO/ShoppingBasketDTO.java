package org.liftoff.saintlouisfarms.models.DTO;

import org.liftoff.saintlouisfarms.models.BasketItem;
import org.liftoff.saintlouisfarms.models.Client;

import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketDTO {
    private List<BasketItem> basketItemsAvailable = new ArrayList<>();

    public ShoppingBasketDTO() {
    }

    @OneToOne
    private Client client;

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

    public void updateBasketItemQuantity(int index, int newQuantity) {
        basketItemsAvailable.get(index).setQuantity(newQuantity);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
