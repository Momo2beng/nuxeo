/*
 * (C) Copyright 2006-2008 Nuxeo SA (http://nuxeo.com/) and others.
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
 *
 */

package org.nuxeo.ecm.platform.commandline.executor.service;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;
import org.nuxeo.common.xmap.registry.XRegistry;
import org.nuxeo.common.xmap.registry.XRegistryId;
import org.nuxeo.ecm.platform.commandline.executor.service.cmdtesters.CommandTester;

/**
 * XMap descriptor for a new contributed {@link CommandTester}.
 *
 * @author tiry
 */
@XObject("commandTester")
@XRegistry
public class CommandTesterDescriptor {

    @XNode(value = "@name", fallback = "@class")
    @XRegistryId
    protected String name;

    @XNode("@class")
    protected Class<CommandTester> className;

    public String getName() {
        return name;
    }

    public Class<CommandTester> getTesterClass() {
        return className;
    }

}
