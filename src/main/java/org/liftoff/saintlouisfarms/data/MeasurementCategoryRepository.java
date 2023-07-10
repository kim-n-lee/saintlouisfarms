package org.liftoff.saintlouisfarms.data;

import org.liftoff.saintlouisfarms.models.MeasurementCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementCategoryRepository extends CrudRepository<MeasurementCategory, Integer> {
    List<MeasurementCategory> findAllById(int id);
}