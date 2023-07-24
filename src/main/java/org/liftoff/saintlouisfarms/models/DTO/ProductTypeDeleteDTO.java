package org.liftoff.saintlouisfarms.models.DTO;

import lombok.Data;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ProductCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class ProductTypeDeleteDTO {
    private List<Product> productsToReassign = new ArrayList<>();

    public ProductTypeDeleteDTO() {
    }
    public List<Product> getProductsToReassign() {
        return productsToReassign;
    }

    public void setProductsToReassign(List<Product> productsToReassign) {
        this.productsToReassign = productsToReassign;
    }
}
