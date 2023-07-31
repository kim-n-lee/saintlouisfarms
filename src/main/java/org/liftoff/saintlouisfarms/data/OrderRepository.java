package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.FarmOrder;
import org.liftoff.saintlouisfarms.models.Product;
import org.liftoff.saintlouisfarms.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<FarmOrder, Integer> {
    @Query
    Optional<List<FarmOrder>> findByFarmerAndSentTrue(User farmer);

}
