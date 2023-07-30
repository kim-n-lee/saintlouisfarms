package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.FarmOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<FarmOrder, Integer> {
}
