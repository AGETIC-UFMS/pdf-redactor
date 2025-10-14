package br.ufms.pdf.redactor.controller;

import br.ufms.pdf.redactor.model.ManualRedactPdfRequest;
import br.ufms.pdf.redactor.model.RedactionArea;

import br.ufms.pdf.redactor.utils.StringToArrayListPropertyEditor;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleaner;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Slf4j
public class RedactController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(
                List.class, "redactions", new StringToArrayListPropertyEditor());
    }

    @PostMapping(value = "/redact", consumes = "multipart/form-data")
    @Operation(
            summary = "Redact areas in a PDF file",
            description = "Redact specified areas in a PDF file and return the modified file.")
    public ResponseEntity<byte[]> redact(@ModelAttribute ManualRedactPdfRequest request) throws IOException {
        MultipartFile file = request.getFileInput();
        List<RedactionArea> redactionAreas = request.getRedactions();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfReader reader = new PdfReader(file.getInputStream());
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdfDoc = new PdfDocument(reader, writer)) {

            redactAreas(redactionAreas, pdfDoc);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalFilename())
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

    private void redactAreas(
            List<RedactionArea> redactionAreas, PdfDocument pdfDoc)
            throws IOException {
        Map<Integer, List<RedactionArea>> redactionsByPage = redactionAreas.stream()
                .filter(r -> r.getPage() != null && r.getPage() > 0)
                .collect(Collectors.groupingBy(RedactionArea::getPage));

        // Process each page only once
        for (Map.Entry<Integer, List<RedactionArea>> entry : redactionsByPage.entrySet()) {
            int pageNumber = entry.getKey();
            List<PdfCleanUpLocation> cleanUpLocations  = new ArrayList<>();

            // Process all redactions
            for (RedactionArea redactionArea : entry.getValue()) {
                Color redactColor = decodeOrDefault(redactionArea.getColor(), Color.BLACK);
                float x = redactionArea.getX().floatValue();
                float y = redactionArea.getY().floatValue();
                float width = redactionArea.getWidth().floatValue();
                float height = redactionArea.getHeight().floatValue();

                float pdfY = pdfDoc.getPage(pageNumber).getPageSize().getHeight() - y - height;

                cleanUpLocations.add(
                        new PdfCleanUpLocation(
                                pageNumber,
                                new Rectangle(x, pdfY, width, height),
                                new DeviceRgb(redactColor.getRed(), redactColor.getGreen(), redactColor.getBlue())
                        ));
            }

            PdfCleaner.cleanUp(pdfDoc, cleanUpLocations);
        }

    }

    private Color decodeOrDefault(String hex, Color defaultColor) {
        Color color = null;
        try {
            color = Color.decode(hex);
        } catch (Exception e) {
            color = defaultColor;
        }

        return color;
    }
}

