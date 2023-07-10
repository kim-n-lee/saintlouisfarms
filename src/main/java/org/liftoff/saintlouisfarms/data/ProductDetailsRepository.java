package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.ProductDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailsRepository extends CrudRepository<ProductDetails, Integer> {
    List<ProductDetails> findAllById(int id);}