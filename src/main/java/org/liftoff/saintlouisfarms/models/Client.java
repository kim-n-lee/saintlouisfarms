package org.liftoff.saintlouisfarms.models;

import javax.persistence.Entity;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends MainUser {


//    public List<User> getUsers() {
//        return users;
//    }

//    @OneToMany(mappedBy = "client")
//    private final List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "client")
    private List<FarmOrder> farmOrders = new ArrayList<>();
    @OneToMany(mappedBy = "client")
    private List<ShoppingBasket> shoppingBaskets = new ArrayList<>();
    public Client() {

    }

    public List<ShoppingBasket> getShoppingBaskets() {
        return shoppingBaskets;
    }

    public void setShoppingBaskets(List<ShoppingBasket> shoppingBaskets) {
        this.shoppingBaskets = shoppingBaskets;
    }

    public Client(String email, String pwHash, String firstName, String lastName, String address, String zip, String city, String phone) {
        super(email, pwHash, firstName, lastName, address, zip, city, phone);
    }

    public List<FarmOrder> getOrders() {
        return farmOrders;
    }

    public void setOrders(List<FarmOrder> farmOrders) {
        this.farmOrders = farmOrders;
    }
}