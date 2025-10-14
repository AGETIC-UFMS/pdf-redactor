package br.ufms.pdf.redactor.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ManualRedactPdfRequest extends PDFWithPageNums {
    @Schema(description = "A list of areas to be redacted")
    private List<RedactionArea> redactions;
}
