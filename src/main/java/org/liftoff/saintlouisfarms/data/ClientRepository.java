package org.liftoff.saintlouisfarms.data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.liftoff.saintlouisfarms.models.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    @Query
    Client findByEmail(String email);
    @Query
    Client findById(int id);
}
