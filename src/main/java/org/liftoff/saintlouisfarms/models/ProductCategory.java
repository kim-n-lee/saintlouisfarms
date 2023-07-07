package org.liftoff.saintlouisfarms.models;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ProductCategory extends AbstractEntity{


    @Size(min = 3, max = 45, message="name must be between 3 and 45 character")
    private String name;


    @OneToMany(mappedBy = "productCategory")
    private final List<Product> products = new ArrayList<>();

    public ProductCategory( @Size(min = 3, max = 45, message="name must be between 3 and 45 character") String name) {
        this.name = name;
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