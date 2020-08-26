package org.ft.client.resource;

/**
 * @author Prajwal Das
 */

import lombok.AllArgsConstructor;
import org.ft.client.service.FeatureToggleService;
import org.ft.core.api.model.FeatureInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/features")
@AllArgsConstructor
public class FeatureToggleResource
{
    private FeatureToggleService featureToggleService;

    @GetMapping("/{featureName}")
    public FeatureInfo getFeatureStatus(@PathVariable String featureName)
    {
        return featureToggleService.getFeatureStatusFromService(featureName);
    }
}
