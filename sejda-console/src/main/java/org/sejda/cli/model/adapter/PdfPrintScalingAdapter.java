/*
 * Created on Oct 5, 2011
 * Copyright 2010 by Eduard Weissmann (edi.weissmann@gmail.com).
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.sejda.cli.model.adapter;

import org.sejda.core.manipulation.model.pdf.viewerpreferences.PdfPrintScaling;

/**
 * Adapter class for enum {@link PdfPrintScaling}. Provides initialization from string
 * 
 * @author Eduard Weissmann
 * 
 */
public class PdfPrintScalingAdapter extends EnumAdapter<PdfPrintScaling> {

    public PdfPrintScalingAdapter(String userFriendlyName) {
        super(userFriendlyName, PdfPrintScaling.class, "print scaling");
    }
}
