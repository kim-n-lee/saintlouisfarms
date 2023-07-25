package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingBasketRepository extends CrudRepository<ShoppingBasket, Integer> {
    @Query(value = "select * from shoppingbasket left join product on shoppingbasket.products_id=product.id left join client on shoppingbasket.client_id=client.id  where shoppingbasket.products_id= ?1 and shoppingbasket.client_id= ?2 ", nativeQuery = true)
    ShoppingBasket findByClientAndProduct(int id, int id2);
}
