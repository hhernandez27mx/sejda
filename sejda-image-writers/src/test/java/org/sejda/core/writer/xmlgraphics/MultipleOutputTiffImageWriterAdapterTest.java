/*
 * Created on 25/set/2011
 * Copyright 2011 by Andrea Vacondio (andrea.vacondio@gmail.com).
 * 
 * This file is part of the Sejda source code
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sejda.core.writer.xmlgraphics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.sejda.ImageTestUtils;
import org.sejda.core.writer.xmlgraphics.MultipleOutputTiffImageWriterAdapter.MultipleOutputTiffImageWriterAdapterBuilder;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.image.ImageColorType;
import org.sejda.model.parameter.image.PdfToMultipleTiffParameters;

/**
 * @author Andrea Vacondio
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MultipleOutputTiffImageWriterAdapter.class)
public class MultipleOutputTiffImageWriterAdapterTest {

    private MultipleOutputTiffImageWriterAdapter victim;

    @Before
    public void setUp() {
        victim = spy(new MultipleOutputTiffImageWriterAdapterBuilder().build());
    }

    @Test
    public void openStreamDestination() {
        OutputStream destination = mock(OutputStream.class);
        PdfToMultipleTiffParameters params = mock(PdfToMultipleTiffParameters.class);
        victim.openWriteDestination(destination, params);
        verify(victim).setOutputStream(destination);
    }

    @Test
    public void openFileDestination() throws TaskIOException, IOException {
        File destination = File.createTempFile("test", ".tmp");
        destination.deleteOnExit();
        PdfToMultipleTiffParameters params = mock(PdfToMultipleTiffParameters.class);
        victim.openWriteDestination(destination, params);
        verify(victim).setOutputStream(any(OutputStream.class));
    }

    @Test(expected = TaskIOException.class)
    public void writeNotOpened() throws TaskIOException {
        PdfToMultipleTiffParameters params = mock(PdfToMultipleTiffParameters.class);
        RenderedImage image = mock(RenderedImage.class);
        victim.write(image, params);
    }

    @Test
    public void supportMultiImage() {
        assertFalse(victim.supportMultiImage());
    }

    @Test
    public void write() throws IOException, TaskIOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("image/test.tiff");
        File destination = File.createTempFile("test", ".tmp");
        destination.deleteOnExit();
        PdfToMultipleTiffParameters params = new PdfToMultipleTiffParameters(ImageColorType.GRAY_SCALE);
        RenderedImage image = ImageTestUtils.loadImage(stream, "test.tiff");
        victim.openWriteDestination(destination, params);
        victim.write(image, params);
        victim.closeDestination();
        victim.close();
        RenderedImage result = ImageTestUtils.loadImage(destination);
        assertTrue(result.getHeight() > 0);
        assertTrue(result.getWidth() > 0);
    }
}
