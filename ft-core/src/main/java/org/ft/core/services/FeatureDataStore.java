package org.ft.core.services;

import org.ft.core.api.model.FeatureInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author Prajwal Das
 */
public interface FeatureDataStore
{
    void enable(String featureId, String tenant);

    void disable(String featureId, String tenant);

    Optional<FeatureInfo> getFeature(String featureId, String tenant);

    List<FeatureInfo> getFeatures(String tenant);

    Optional<FeatureInfo> create(FeatureInfo feature);

    List<FeatureInfo> createOrUpdate (List<FeatureInfo> features);

    Optional<FeatureInfo> update(FeatureInfo feature);

    void delete(String featureId);

    List<String> getAllTenantsIdentifiers ();
}
