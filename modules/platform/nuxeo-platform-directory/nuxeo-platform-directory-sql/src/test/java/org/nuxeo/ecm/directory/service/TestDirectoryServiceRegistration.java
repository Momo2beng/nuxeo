/*
 * (C) Copyright 2012-2017 Nuxeo (http://nuxeo.com/) and others.
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
 *     Anahide Tchertchian
 */
package org.nuxeo.ecm.directory.service;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.directory.Directory;
import org.nuxeo.ecm.directory.api.DirectoryService;
import org.nuxeo.ecm.directory.memory.MemoryDirectory;
import org.nuxeo.ecm.directory.sql.SQLDirectory;
import org.nuxeo.ecm.directory.sql.SQLDirectoryFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

/**
 * Test hot reload of registrations using mock directory factories
 *
 * @since 5.6
 */
@RunWith(FeaturesRunner.class)
@Features(SQLDirectoryFeature.class)
@RepositoryConfig(cleanup = Granularity.METHOD)
@Deploy("org.nuxeo.ecm.directory.sql.tests:test-sql-directories-schema-override.xml")
@Deploy("org.nuxeo.ecm.directory.sql.tests:test-sql-directories-bundle.xml")
public class TestDirectoryServiceRegistration {

    @Inject
    protected DirectoryService directoryService;

    @Test
    public void testWithoutOverride() {
        Directory dir = directoryService.getDirectory("userDirectory");
        assertTrue(dir instanceof SQLDirectory);
    }

    @Test
    @Deploy("org.nuxeo.ecm.directory.sql.tests:test-directories-several-factories.xml")
    public void testOverride() {
        Directory dir = directoryService.getDirectory("userDirectory");
        assertTrue(dir instanceof MemoryDirectory);
    }

}
