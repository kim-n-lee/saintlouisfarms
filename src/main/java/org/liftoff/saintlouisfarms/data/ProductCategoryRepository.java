package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {

    ProductCategory findByName(String name);

    List<ProductCategory> findAllById(int id);
}