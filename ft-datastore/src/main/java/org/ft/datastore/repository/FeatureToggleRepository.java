package org.ft.datastore.repository;

import org.ft.datastore.models.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Prajwal Das
 */
@Repository
public interface FeatureToggleRepository extends JpaRepository<Feature, String>
{

    @Query("Select f from Feature f where f.enableOn = :enableOn")
    List<Feature> getFeatureByActivationDate (LocalDate enableOn);

    @Modifying
    @Transactional
    @Query("update Feature f set f.active = false where f.id = :featureId")
    void deactivateFeature (String featureId);
}
