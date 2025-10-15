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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RedactionArea {
    @Schema(description = "The left edge point of the area to be redacted.")
    private Double x;

    @Schema(description = "The top edge point of the area to be redacted.")
    private Double y;

    @Schema(description = "The height of the area to be redacted.")
    private Double height;

    @Schema(description = "The width of the area to be redacted.")
    private Double width;

    @Schema(description = "The page on which the area should be redacted.")
    private Integer page;

    @Schema(description = "The color used to redact the specified area.")
    private String color;
}
