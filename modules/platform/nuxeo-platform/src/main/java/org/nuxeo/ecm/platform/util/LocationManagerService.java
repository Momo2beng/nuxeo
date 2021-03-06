/*
 * (C) Copyright 2006-2007 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Nuxeo - initial API and implementation
 *
 */

package org.nuxeo.ecm.platform.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * @author Thierry Delprat
 */
public class LocationManagerService extends DefaultComponent {

    private Map<String, RepositoryLocation> locations;

    @Override
    public void start(ComponentContext context) {
        locations = new HashMap<>();
        this.<LocationManagerPluginExtension> getRegistryContributions("location").forEach(c -> {
            String name = c.getLocationName();
            locations.put(name, new RepositoryLocation(name));
        });
    }

    @Override
    public void stop(ComponentContext context) throws InterruptedException {
        locations = null;
    }

    public Map<String, RepositoryLocation> getAvailableLocations() {
        return Collections.unmodifiableMap(locations);
    }

}
