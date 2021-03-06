/*
 * (C) Copyright 2010 Nuxeo SA (http://nuxeo.com/) and others.
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
 * Contributors:
 * Nuxeo - initial API and implementation
 */

package org.nuxeo.ecm.platform.types.localconfiguration;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.nuxeo.ecm.core.api.localconfiguration.LocalConfiguration;
import org.nuxeo.ecm.platform.types.SubType;

/**
 * Local configuration class to handle configuration of UI Types.
 *
 * @author <a href="mailto:troger@nuxeo.com">Thomas Roger</a>
 */
public interface UITypesConfiguration extends LocalConfiguration<UITypesConfiguration> {

    /**
     * Returns the configured allowed types.
     */
    List<String> getAllowedTypes();

    /**
     * Returns the configured denied types.
     */
    List<String> getDeniedTypes();

    /**
     * Returns {@code true} if all the types are denied, {@code false} otherwise.
     */
    boolean denyAllTypes();

    /**
     * Filter the {@code allowedSubTypes} according to this object configuration.
     */
    Map<String, SubType> filterSubTypes(Map<String, SubType> allowedSubTypes);

    /**
     * Filters the {@code allowedSubTypes} according to this object configuration.
     *
     * @since 11.5
     */
    Collection<String> filterSubTypes(Collection<String> allowedSubTypes);

    String getDefaultType();
}
