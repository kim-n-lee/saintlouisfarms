package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.Client;
import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientShoppingBasketRepository extends CrudRepository<ShoppingBasket, Integer> {
}
