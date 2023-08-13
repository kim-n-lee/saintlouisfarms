package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
}
