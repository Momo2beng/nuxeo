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
 *     bstefanescu
 *
 */

package org.nuxeo.ecm.webengine.security.guards;

import org.nuxeo.common.xmap.annotation.XContent;
import org.nuxeo.common.xmap.annotation.XObject;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.webengine.security.Guard;
import org.nuxeo.runtime.model.Adaptable;

/**
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
@XObject("type")
public class TypeGuard implements Guard {

    @XContent
    protected String type;

    protected TypeGuard() {
    }

    public TypeGuard(String type) {
        this.type = type;
    }

    @Override
    public boolean check(Adaptable context) {
        return context.getAdapter(DocumentModel.class).getType().equals(type);
    }

    @Override
    public String toString() {
        return "TYPE[" + type + ']';
    }

}
