package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {

    ProductCategory findByName(String name);
}