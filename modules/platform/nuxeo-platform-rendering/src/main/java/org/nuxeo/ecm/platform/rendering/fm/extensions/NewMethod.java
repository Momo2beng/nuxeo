/*
 * (C) Copyright 2006-2011 Nuxeo SA (http://nuxeo.com/) and others.
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

package org.nuxeo.ecm.platform.rendering.fm.extensions;

import java.lang.reflect.Constructor;
import java.util.List;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * @author <a href="mailto:bs@nuxeo.com">Bogdan Stefanescu</a>
 */
public class NewMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
        int size = arguments.size();
        if (size < 1) {
            throw new TemplateModelException("Invalid number of arguments for new(class, ...) method");
        }

        Class<?> klass;
        try {
            String className = (String) arguments.get(0);
            klass = Class.forName(className);
            if (size == 1) {
                return klass.getDeclaredConstructor().newInstance();
            }
        } catch (ReflectiveOperationException e) {
            throw new TemplateModelException("Failed to isntantiate the object", e);
        }
        arguments.remove(0);
        Object[] ar = arguments.toArray();
        size--;
        Constructor<?>[] ctors = klass.getConstructors();
        for (Constructor<?> ctor : ctors) {
            Class<?>[] params = ctor.getParameterTypes(); // this is cloning params
            if (params.length == size) { // try this one
                try {
                    return ctor.newInstance(ar);
                } catch (ReflectiveOperationException e) {
                    // continue
                }
            }
        }
        throw new TemplateModelException("No suitable constructor found");
    }

}
