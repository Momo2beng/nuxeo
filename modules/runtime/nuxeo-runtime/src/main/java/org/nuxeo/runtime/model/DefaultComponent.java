/*
 * (C) Copyright 2006-2020 Nuxeo (http://nuxeo.com/) and others.
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
 *     Bogdan Stefanescu
 *     Anahide Tchertchian
 */

package org.nuxeo.runtime.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import org.nuxeo.common.xmap.registry.MapRegistry;
import org.nuxeo.common.xmap.registry.Registry;
import org.nuxeo.common.xmap.registry.SingleRegistry;
import org.nuxeo.runtime.RuntimeMessage;
import org.nuxeo.runtime.RuntimeMessage.Level;
import org.nuxeo.runtime.RuntimeMessage.Source;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.model.impl.ComponentManagerImpl;

/**
 * Default implementation for a component, to be extended by contributed components.
 */
public class DefaultComponent implements Component, Adaptable {

    /** @since 10.3 */
    protected String name;

    /**
     * @since 5.6
     */
    protected Long lastModified;

    private DescriptorRegistry registry;

    @Override
    public void setName(String name) {
        this.name = requireNonNull(name);
    }

    @Override
    public void activate(ComponentContext context) {
        registry = ((ComponentManagerImpl) context.getRuntimeContext()
                                                  .getRuntime()
                                                  .getComponentManager()).getDescriptors();
        setModifiedNow();
    }

    @Override
    public void deactivate(ComponentContext context) {
        if (getRegistry() != null) {
            getRegistry().clear();
        }
        setModifiedNow();
    }

    protected void addRuntimeMessage(Level level, String message) {
        addRuntimeMessage(level, message, Source.COMPONENT, name);
    }

    protected void addRuntimeMessage(Level level, String message, Source source, String sourceComponent) {
        Framework.getRuntime()
                 .getMessageHandler()
                 .addMessage(new RuntimeMessage(level, message, source, sourceComponent));
    }

    @Override
    public void registerExtension(Extension extension) {
        Object[] contribs = extension.getContributions();
        if (contribs == null) {
            return;
        }
        for (Object contrib : contribs) {
            registerContribution(contrib, extension.getExtensionPoint(), extension.getComponent());
        }
        setModifiedNow();
    }

    @Override
    public void unregisterExtension(Extension extension) {
        Object[] contribs = extension.getContributions();
        if (contribs == null) {
            return;
        }
        for (Object contrib : contribs) {
            unregisterContribution(contrib, extension.getExtensionPoint(), extension.getComponent());
        }
        setModifiedNow();
    }

    /**
     * @deprecated since 11.5: contributions registration should use a {@link Registry}.
     */
    @Deprecated(since = "11.5")
    public void registerContribution(Object contribution, String xp, ComponentInstance component) {
        if (contribution instanceof Descriptor) {
            register(xp, (Descriptor) contribution);
        }
    }

    /**
     * @deprecated since 11.5: contributions unregistration should use a {@link Registry}.
     */
    @Deprecated(since = "11.5")
    public void unregisterContribution(Object contribution, String xp, ComponentInstance component) {
        if (contribution instanceof Descriptor) {
            unregister(xp, (Descriptor) contribution);
        }
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return adapter.cast(this);
    }

    @Override
    public void start(ComponentContext context) {
        // delegate for now to applicationStarted
        applicationStarted(context);
    }

    @Override
    public void stop(ComponentContext context) throws InterruptedException {
    }

    /**
     * Sets the last modified date to current date timestamp
     *
     * @since 5.6
     */
    protected void setModifiedNow() {
        setLastModified(Long.valueOf(System.currentTimeMillis()));
    }

    @Override
    public Long getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @since 10.3
     * @deprecated since 11.5: use {@link Registry} associated annotations instead.
     * @see #getExtensionPointRegistry(String)
     */
    @Deprecated(since = "11.5")
    protected DescriptorRegistry getRegistry() {
        return registry;
    }

    /**
     * @since 10.3
     * @deprecated since 11.5: use {@link Registry} associated annotations instead.
     */
    @Deprecated(since = "11.5")
    protected boolean register(String xp, Descriptor descriptor) {
        return getRegistry().register(name, xp, descriptor);
    }

    /**
     * @since 10.3
     * @deprecated since 11.5: use {@link Registry} associated annotations instead.
     */
    @Deprecated(since = "11.5")
    protected boolean unregister(String xp, Descriptor descriptor) {
        return getRegistry().unregister(name, xp, descriptor);
    }

    /**
     * @since 10.3
     * @deprecated since 11.5: use {@link Registry} associated annotations instead.
     * @see #getRegistryContribution(String, String)
     */
    @Deprecated(since = "11.5")
    protected <T extends Descriptor> T getDescriptor(String xp, String id) {
        return getRegistry().getDescriptor(name, xp, id);
    }

    /**
     * @since 10.3
     * @deprecated since 11.5: use {@link Registry} associated annotations instead.
     * @see #getRegistryContributions(String)
     */
    @Deprecated(since = "11.5")
    protected <T extends Descriptor> List<T> getDescriptors(String xp) {
        return getRegistry().getDescriptors(name, xp);
    }

    /**
     * Returns the registry for given extension point of this component.
     *
     * @since 11.5
     */
    @SuppressWarnings("unchecked")
    protected <T extends Registry> T getExtensionPointRegistry(String point) {
        return (T) Framework.getRuntime()
                            .getComponentManager()
                            .getExtensionPointRegistry(name, point)
                            .orElseThrow(() -> new IllegalArgumentException(
                                    String.format("Unknown registry for extension point '%s--%s'", name, point)));
    }

    /**
     * Returns a single contribution from given target extension point.
     * <p>
     * Assumes the registry implements {@link SingleRegistry}.
     *
     * @since 11.5
     */
    protected <T> Optional<T> getRegistryContribution(String point) {
        SingleRegistry registry = getExtensionPointRegistry(point);
        return registry.getContribution();
    }

    /**
     * Returns a single contribution with given id from given target extension point.
     * <p>
     * Assumes the registry implements {@link MapRegistry}.
     *
     * @since 11.5
     */
    protected <T> Optional<T> getRegistryContribution(String point, String id) {
        MapRegistry registry = getExtensionPointRegistry(point);
        return registry.getContribution(id);
    }

    /**
     * Returns a list of contributions from given target extension point.
     * <p>
     * Assumes the registry implements {@link MapRegistry}.
     *
     * @since 11.5
     */
    protected <T> List<T> getRegistryContributions(String point) {
        MapRegistry registry = getExtensionPointRegistry(point);
        return registry.getContributionValues();
    }

}
