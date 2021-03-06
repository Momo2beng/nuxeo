/*
 * (C) Copyright 2014-2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Florent Guillaume
 */
package org.nuxeo.ecm.directory.core;

import org.nuxeo.ecm.directory.AbstractDirectoryDescriptorRegistry;
import org.nuxeo.ecm.directory.DirectoryRegistry;
import org.nuxeo.runtime.model.DefaultComponent;

/**
 * Factory implementation for directory on repository.
 *
 * @since 8.2
 */
public class CoreDirectoryFactory extends DefaultComponent {

    protected static final String COMPONENT_NAME = "org.nuxeo.ecm.directory.core.CoreDirectoryFactory";

    /**
     * Registry for {@link CoreDirectoryDescriptor}, forwarding to {@link DirectoryRegistry}.
     * <p>
     * Also handles custom merge.
     *
     * @since 11.5
     */
    public static final class Registry extends AbstractDirectoryDescriptorRegistry {

        public Registry() {
            super(COMPONENT_NAME);
        }

    }

}
