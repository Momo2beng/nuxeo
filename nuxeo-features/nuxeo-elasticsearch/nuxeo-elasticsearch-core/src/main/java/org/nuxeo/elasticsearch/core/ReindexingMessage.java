/*
 * (C) Copyright 2021 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     bdelbosc
 */
package org.nuxeo.elasticsearch.core;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.nuxeo.runtime.pubsub.SerializableMessage;

/**
 * @since 2021.9
 */
public class ReindexingMessage implements SerializableMessage {
    private static final long serialVersionUID = 20210903L;

    public final String bulkIndexCommandId;

    public final String indexName;
    public final String secondWriteIndexName;

    public final ReindexingState state;

    /**
     *
     * @param bulkCommandId the bulk command id that is performing the re-indexation
     * @param indexName the name of the search index
     * @param secondIndexName the name of the second write index
     * @param state the state of the reindexing
     */
    public ReindexingMessage(String bulkCommandId, String indexName, String secondIndexName, ReindexingState state) {
        this.bulkIndexCommandId = bulkCommandId;
        this.indexName = indexName;
        this.secondWriteIndexName = secondIndexName;
        this.state = state;
    }

    @Override
    public void serialize(OutputStream out) throws IOException {
        IOUtils.write(bulkIndexCommandId, out, UTF_8);
        IOUtils.write(indexName, out, UTF_8);
        IOUtils.write(secondWriteIndexName, out, UTF_8);
        IOUtils.write(String.valueOf(state), out, UTF_8);
    }

    public static ReindexingMessage deserialize(InputStream in) throws IOException {
        String id = IOUtils.toString(in, UTF_8);
        String index = IOUtils.toString(in, UTF_8);
        String writeIndex = IOUtils.toString(in, UTF_8);
        ReindexingState state = ReindexingState.valueOf(IOUtils.toString(in, UTF_8));
        return new ReindexingMessage(id, index, writeIndex, state);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + bulkIndexCommandId + "," + secondWriteIndexName + ", " + state + ")";
    }
}
