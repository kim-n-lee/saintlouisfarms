package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query
    User findByEmail(String email);

    @Query
    User findById(int id);


}
