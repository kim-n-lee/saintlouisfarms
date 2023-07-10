package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.liftoff.saintlouisfarms.models.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementCategoryRepository extends CrudRepository<MeasurementCategory, Integer> {
    @Query(value = "select * from measurementcategory left join user on measurementcategory.user_id=user.id where   user.id = ?1",nativeQuery = true)
    List<MeasurementCategory> findMeasurementById(int id);
}