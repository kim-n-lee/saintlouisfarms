package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementCategoryRepository extends CrudRepository<MeasurementCategory, Integer> {
    @Query(value = "select * from measurementCategory left join user on measurementCategory.user_id=user.id where   user.id = ?1",nativeQuery = true)
    List<MeasurementCategory> findMeasurementById(int id);

    @Query(value = "select * from product p left join measurementcategory m on p.measurementcategory_id=m.id  where  p.user_id= ?1 and m.name= ?2",nativeQuery = true)
    Iterable<Product> findAllProductAssignedToMeasurment(int id, String measurementToDelete);

}