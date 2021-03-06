/*
 * (C) Copyright 2017-2021 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Mariana Cedica
 */
package org.nuxeo.ecm.core.management.statuses;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nuxeo.ecm.core.management.api.ProbeInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Returns the status of the application
 *
 * @since 9.3
 */
public class HealthCheckResult {

    private static final Logger log = LogManager.getLogger(HealthCheckResult.class);

    protected static final String EMPTY_JSON = "{}";

    protected final Collection<ProbeInfo> probes;

    protected final boolean healthy;

    public HealthCheckResult(Collection<ProbeInfo> probesToCheck) {
        this.probes = probesToCheck;
        healthy = probes.stream().allMatch(p -> p.getStatus().isSuccess());
        if (!healthy) {
            log.warn("HealthCheck is not healthy, see probes: {}", probes);
        }
    }

    public boolean isHealthy() {
        return healthy;
    }

    public String toJson() {
        ObjectMapper om = new ObjectMapper();
        Map<String, String> res = new HashMap<>();
        try {
            for (ProbeInfo probe : probes) {
                res.put(probe.getShortcutName(), (probe.getStatus().isSuccess() ? "ok" : "failed"));
            }
            return om.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            log.error("Unable to write HealthCheckResult to json", e);
            return EMPTY_JSON;
        }
    }
}
