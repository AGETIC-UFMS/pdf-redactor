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

import br.ufms.pdf.redactor.utils.GeneralUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class PDFWithPageNums extends PDFFile {
    @Schema(
            description =
                    "The pages to select, Supports ranges (e.g., '1,3,5-9'), or 'all' or functions in the"
                            + " format 'an+b' where 'a' is the multiplier of the page number 'n', and 'b' is a"
                            + " constant (e.g., '2n+1', '3n', '6n-5')\"",
            defaultValue = "all",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String pageNumbers;

    @Hidden
    public List<Integer> getPageNumbersList(PDDocument doc, boolean oneBased) {
        int pageCount = 0;
        pageCount = doc.getNumberOfPages();
        return GeneralUtils.parsePageList(pageNumbers, pageCount, oneBased);
    }
}
