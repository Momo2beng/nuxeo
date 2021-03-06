/*
 * (C) Copyright 2006-2012 Nuxeo SA (http://nuxeo.com/) and others.
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
 *     Nuxeo
 *     Antoine Taillefer
 */

package org.nuxeo.ecm.core.convert.plugins.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.common.utils.FileUtils;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
public class TestMSOfficeConverter extends SimpleConverterTest {

    // Word POI tests fails in surefire
    @Test
    public void testWordConverter() throws IOException {
        doTestTextConverter("application/msword", "msoffice2text", "hello.doc");
    }

    @Test
    public void testWordArabicConverter() throws IOException {
        doTestArabicTextConverter("application/msword", "msoffice2text", "wikipedia-internet-ar.doc");
    }

    @Test
    public void testPptConverter() throws IOException {
        doTestTextConverter("application/vnd.ms-powerpoint", "msoffice2text", "hello.ppt");
    }

    @Test
    public void testXlsConverter() throws IOException {
        doTestTextConverter("application/vnd.ms-excel", "xl2text", "hello.xls");
    }

    @Test
    public void testDocxConverter() throws IOException {
        doTestTextConverter("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx2text",
                "hello.docx");
    }

    @Test
    public void testRtfConverter() throws IOException {
        doTestTextConverter("application/rtf", "rtf2text", "hello.rtf");
        doTestTextConverter("text/rtf", "rtf2text", "hello.rtf");
    }

    @Test
    public void testPptxConverter() throws IOException {
        doTestTextConverter("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx2text",
                "hello.pptx");
    }

    @Test
    public void testXlsxConverter() throws IOException {
        doTestTextConverter("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlx2text",
                "hello.xlsx");
    }

    /**
     * Tests XLSLX text converter fallback with blob length > max size for POI.
     *
     * @since 11.5
     */
    @Test
    @Deploy("org.nuxeo.ecm.core.convert.plugins.test.test:test-convert-service-contrib.xml")
    public void testXlsxConverterFallbackMaxSize() throws IOException {
        doTestTextConverter("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlx2text",
                "hello.xlsx");
    }

    /**
     * Tests XLSLX text converter fallback with blob length == -1.
     *
     * @since 11.5
     */
    // NXP-30294
    @Test
    public void testXlsxConverterFallbackUnknownLength() throws IOException {
        Blob blob = mock(Blob.class);
        when(blob.getLength()).thenReturn(-1L);
        when(blob.getMimeType()).thenReturn("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        when(blob.getStream()).then(
                unused -> new FileInputStream(FileUtils.getResourceFileFromContext("test-docs/hello.xlsx")));
        doTestTextConverterBlob("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlx2text", blob);
    }

    @Test
    public void testAnyToTextConverterWord() throws IOException {
        doTestAny2TextConverter("application/msword", "msoffice2text", "hello.doc");
    }

    @Test
    public void testAnyToTextExcelConverter() throws IOException {
        doTestAny2TextConverter("application/vnd.ms-excel", "xl2text", "hello.xls");
    }

    @Test
    public void testAnyToTextDocxConverter() throws IOException {
        doTestAny2TextConverter("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx2text",
                "hello.docx");
    }

    @Test
    public void testAnyToTextXlsxConverter() throws IOException {
        doTestAny2TextConverter("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlx2text",
                "hello.xlsx");
    }
}
