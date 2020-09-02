package org.ft.server.resource;

import lombok.AllArgsConstructor;
import org.ft.core.api.model.FeatureInfo;
import org.ft.core.api.model.Phase;
import org.ft.core.exceptions.FeatureToggleException;
import org.ft.core.request.FeatureTogglesRequest;
import org.ft.core.response.FeatureToggleResponse;
import org.ft.core.services.FeatureDataStore;
import org.ft.core.services.TenantIdentifierService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Prajwal Das
 */
@RestController
@AllArgsConstructor
@RequestMapping("/features")
public class FeatureToggleResource
{
    private FeatureDataStore ftService;


    @PostMapping
    public FeatureInfo createFeature (@RequestBody FeatureInfo featureInfo)
    {
        return ftService.createOrUpdate(featureInfo).orElseThrow(() -> FeatureToggleException.FEATURE_NOT_FOUND);
    }

    @PostMapping("/bulk")
    public FeatureToggleResponse createFeatures (@RequestBody FeatureTogglesRequest request)
    {
        return FeatureToggleResponse.builder().features(ftService.createOrUpdate(request.getFeatures())).build();
    }

    @GetMapping
    public FeatureToggleResponse getAllFeatureForTenant (@RequestParam Phase phase,
                                                         @RequestParam(defaultValue = TenantIdentifierService.DEFAULT) String tenant)
    {
        return FeatureToggleResponse.builder().features(ftService.getFeatures(tenant, phase)).build();
    }

    @PostMapping("/{featureId}/{status}")
    public void setFeatureStatus (@PathVariable String featureId,
                                  @PathVariable Boolean status,
                                  @RequestParam(defaultValue = TenantIdentifierService.DEFAULT) String tenant)
    {
        if(status) {
            ftService.enable(featureId, tenant);
        } else {
            ftService.disable(featureId, tenant);
        }
    }

    @PostMapping("/{featureId}/enable")
    public void getEnableFeatureToggle (@PathVariable String featureId,
                                        @RequestParam(defaultValue = TenantIdentifierService.DEFAULT) String tenant)
    {
        ftService.enable(featureId, tenant);
    }

    @PostMapping("/{featureId}/disable")
    public void getDisableFeatureToggle (@PathVariable String featureId,
                                         @RequestParam(defaultValue = TenantIdentifierService.DEFAULT) String tenant)
    {
        ftService.disable(featureId, tenant);
    }

    @GetMapping("/tenants")
    public List<String> getAllTenantsIdentifiers ()
    {
        return ftService.getAllTenantsIdentifiers();
    }

    @GetMapping("/{featureId}")
    public FeatureInfo getFeatureToggle (@PathVariable String featureId,
                                         @RequestParam(defaultValue = TenantIdentifierService.DEFAULT) String tenant,
                                         @RequestParam Phase phase)
    {
        return ftService.getFeature(
            featureId,
            tenant,
            phase).orElseThrow(() -> FeatureToggleException.FEATURE_NOT_FOUND);
    }
}
