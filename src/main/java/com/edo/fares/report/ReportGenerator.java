package com.edo.fares.report;

import com.edo.fares.model.Flight;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ReportGenerator {

    private static final String FONT_PATH = "src/main/resources/fonts/DejaVuSans.ttf";

    public void generatePdfReport(ObservableList<Flight> flights, String filePath) throws IOException, DocumentException {
        if (flights == null || flights.isEmpty()) {
            throw new IllegalArgumentException("No flight data to export.");
        }

        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Load Unicode font
        BaseFont unicodeFont = BaseFont.createFont(
                Paths.get(FONT_PATH).toAbsolutePath().toString(),
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
        );
        Font titleFont = new Font(unicodeFont, 16, Font.BOLD);
        Font headerFont = new Font(unicodeFont, 12, Font.BOLD, BaseColor.WHITE);
        Font cellFont = new Font(unicodeFont, 11, Font.NORMAL);

        // Title
        Paragraph title = new Paragraph("Flight Fare Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // Table
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2f, 3f, 2f, 2f, 2f, 2f});

        addHeaderCell(table, "Code", headerFont);
        addHeaderCell(table, "Airline", headerFont);
        addHeaderCell(table, "Origin", headerFont);
        addHeaderCell(table, "Destination", headerFont);
        addHeaderCell(table, "Date", headerFont);
        addHeaderCell(table, "Price (€)", headerFont);

        for (Flight f : flights) {
            table.addCell(new PdfPCell(new Phrase(f.getCode(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getAirline(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getOrigin(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(f.getDestination(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(f.getDate()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f", f.getPrice()), cellFont)));
        }

        document.add(table);
        document.close();
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(new BaseColor(0, 102, 204)); // Azul profesional
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(6f);
        table.addCell(cell);
    }
}
