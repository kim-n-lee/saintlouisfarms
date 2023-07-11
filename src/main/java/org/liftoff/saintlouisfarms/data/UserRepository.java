package org.liftoff.saintlouisfarms.data;


import org.liftoff.saintlouisfarms.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);

    @Query(value = "SELECT * FROM farm.user where user.id= ?1", nativeQuery = true)
    User findById(int id);


}
