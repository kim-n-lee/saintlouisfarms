package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}