package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query(value = "select * from product left join productdetails on product.productDetails_id=productdetails.id where  product.user_id = ?1", nativeQuery = true)
    List<Product> findProductById(int id);
@Query(value = "select * from product left join productdetails on product.productDetails_id=productdetails.id where productdetails.status=true and product.user_id = ?1", nativeQuery = true)
    List<Product> findProductByStatus(int ids);

@Query(value = "delete  from product where id= ?1", nativeQuery = true)
    List<Product>deleteProductById(int id);

@Query(value = "select *  from product where name= ?1", nativeQuery = true)

List<Product>findNameOfProductBy(String name);
}