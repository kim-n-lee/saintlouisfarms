package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.ShoppingBasket;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketRepository extends CrudRepository<ShoppingBasket, Integer> {
}
