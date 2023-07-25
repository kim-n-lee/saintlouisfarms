package org.liftoff.saintlouisfarms.models;

import javax.persistence.Entity;

@Entity
public class Client extends MainUser {
    public Client() {

    }

    public Client(String email, String pwHash, String firstName, String lastName, String address, String zip, String city, String phone) {
        super(email, pwHash, firstName, lastName, address, zip, city, phone);
    }
}
