/*
 * (C) Copyright 2015-2016 Nuxeo SA (http://nuxeo.com/) and others.
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
 */
package org.nuxeo.automation.scripting.api;

import java.io.InputStream;

import org.nuxeo.automation.scripting.internals.AutomationScriptingParamsInjector;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.core.api.CoreSession;

public interface AutomationScriptingService {

    public interface Session extends AutoCloseable {

        Object run(InputStream input);

        <T> T handleof(InputStream input, Class<T> typeof);

        <T> T adapt(Class<T> typeof);
    }

    Session get(CoreSession session);

    Session get(OperationContext context);

    /**
     * Returns injector used by the service.
     *
     * @since 11.5
     */
    AutomationScriptingParamsInjector getParametersInjector();

}
