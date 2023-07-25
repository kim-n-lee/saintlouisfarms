package org.liftoff.saintlouisfarms.models.DTO;

import org.liftoff.saintlouisfarms.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MultiProductDTO {
    private List<Product> productsToReassign = new ArrayList<>();

    public MultiProductDTO() {
    }
    public List<Product> getProductsToReassign() {
        return productsToReassign;
    }

    public void setProductsToReassign(List<Product> productsToReassign) {
        this.productsToReassign = productsToReassign;
    }
}