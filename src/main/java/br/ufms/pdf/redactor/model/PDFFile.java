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
package br.ufms.pdf.redactor.model;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class PDFFile {
    @Schema(description = "The input PDF file")
    private MultipartFile fileInput;
}
