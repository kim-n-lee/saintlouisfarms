package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Integer> {
    @Query(value = "select * from productcategory left join user on productcategory.user_id=user.id where   user.id= ?1", nativeQuery = true)
    List<ProductCategory> findProductsTypetById(int id);
    ProductCategory findByName(String name);
    @Query(value = "select * from product p left join productcategory c on p.productCategory_id=c.id  where  p.user_id= ?1 and p.productCategory_id= ?2 and product.isDeleted=0 ",nativeQuery = true)
    Iterable<Product> findAllProductAssignedToProductCategory(int id, int CategoryId);
}

