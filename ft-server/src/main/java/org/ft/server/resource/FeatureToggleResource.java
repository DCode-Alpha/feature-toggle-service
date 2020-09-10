/*
 *    Copyright 2019-2030 codecuriosity
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.ft.server.resource;

import lombok.AllArgsConstructor;
import org.ft.core.api.model.FeatureInfo;
import org.ft.core.api.model.Phase;
import org.ft.core.exceptions.FeatureToggleException;
import org.ft.core.request.FeatureTogglesRequest;
import org.ft.core.response.FeatureToggleResponse;
import org.ft.core.services.FeatureDataStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public FeatureToggleResponse createFeature (@RequestBody FeatureInfo featureInfo)
    {
        return ftService.createOrUpdate(featureInfo);
    }

    @PostMapping("/bulk")
    public FeatureToggleResponse createFeatures (@RequestBody FeatureTogglesRequest request)
    {
        return ftService.createOrUpdate(request.getFeatures());
    }

    @GetMapping
    public FeatureToggleResponse getAllFeatureForTenant (@RequestParam Phase phase,
                                                         @RequestParam String tenant)
    {
        return FeatureToggleResponse.builder().features(ftService.getFeatures(tenant, phase)).build();
    }

    @PostMapping("/{featureId}/{status}")
    public void setFeatureStatus (@PathVariable String featureId,
                                  @PathVariable Boolean status,
                                  @RequestParam String tenant)
    {
        if(status) {
            ftService.enable(featureId, tenant);
        } else {
            ftService.disable(featureId, tenant);
        }
    }

    @PostMapping("/{featureId}/enable")
    public void getEnableFeatureToggle (@PathVariable String featureId,
                                        @RequestParam String tenant)
    {
        ftService.enable(featureId, tenant);
    }

    @PostMapping("/{featureId}/disable")
    public void getDisableFeatureToggle (@PathVariable String featureId,
                                         @RequestParam String tenant)
    {
        ftService.disable(featureId, tenant);
    }

    @GetMapping("/{featureId}")
    public FeatureInfo getFeatureToggle (@PathVariable String featureId,
                                         @RequestParam String tenant,
                                         @RequestParam Phase phase)
    {
        return ftService.getFeature(
            featureId,
            tenant,
            phase).orElseThrow(() -> FeatureToggleException.FEATURE_NOT_FOUND);
    }
}
