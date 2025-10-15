/*
    Copyright (C) 2025 Federal University of Mato Grosso do Sul

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

    Third-Party Dependencies
    
    - iText-pdfSweep-Java - AGPLv3 - Source code at <https://github.com/itext/itext-pdfsweep-java>
 */
package br.ufms.pdf.redactor.utils;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import br.ufms.pdf.redactor.model.RedactionArea;


@Slf4j
public class StringToArrayListPropertyEditor extends PropertyEditorSupport {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(new ArrayList<>());
            return;
        }
        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            TypeReference<ArrayList<RedactionArea>> typeRef =
                    new TypeReference<ArrayList<RedactionArea>>() {};
            List<RedactionArea> list = objectMapper.readValue(text, typeRef);
            setValue(list);
        } catch (Exception e) {
            log.error("Exception while converting {}", e);
            throw new IllegalArgumentException(
                    "Failed to convert java.lang.String to java.util.List");
        }
    }
}
