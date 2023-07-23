package org.liftoff.saintlouisfarms.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductCategory extends AbstractEntity{


    @Size(min = 3, max = 45, message="name must be between 3 and 45 character")
    private String name;

    @ManyToOne()
    private User user;
    @OneToMany(mappedBy = "productCategory" ,orphanRemoval = true,cascade = CascadeType.PERSIST)
    private final List<Product> products = new ArrayList<>();

    public ProductCategory( @Size(min = 3, max = 45, message="name must be between 3 and 45 character") String name,User user) {
        this.name = name;
        this.user=user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductCategory() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return name;
    }

}