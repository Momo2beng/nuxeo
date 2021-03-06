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

package org.nuxeo.ecm.platform.login.deputy.management;

import java.util.Calendar;
import java.util.List;

import org.nuxeo.ecm.core.api.DocumentModel;

public interface DeputyManager {

    List<String> getPossiblesAlternateLogins(String userName);

    List<String> getAvalaibleDeputyIds(String userName);

    List<DocumentModel> getAvalaibleMandates(String userName);

    DocumentModel newMandate(String username, String deputy);

    DocumentModel newMandate(String username, String deputy, Calendar start, Calendar end);

    void addMandate(DocumentModel entry);

    void removeMandate(String username, String deputy);

    String getDeputySchemaName();
}
