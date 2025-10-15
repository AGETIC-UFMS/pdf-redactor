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

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class GeneralUtils {
    public static List<Integer> parsePageList(String pages, int totalPages, boolean oneBased) {
        if (pages == null) {
            return List.of(1); // Default to first page if input is null
        }
        try {
            return parsePageList(Arrays.toString(pages.split(",")), totalPages, oneBased);
        } catch (NumberFormatException e) {
            return List.of(1); // Default to first page if input is invalid
        }
    }
}
