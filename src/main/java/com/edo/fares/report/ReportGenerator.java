package com.edo.fares.report;

import com.edo.fares.model.Flight;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class ReportGenerator {

    public void generatePdfReport(List<Flight> flights, String outputPath) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // --- FONT FIX: Cargar fuente embebida o usar fallback estándar ---
        BaseFont baseFont;
        try (InputStream fontStream = getClass().getResourceAsStream("/fonts/DejaVuSans.ttf")) {
            if (fontStream != null) {
                byte[] fontBytes = fontStream.readAllBytes();
                baseFont = BaseFont.createFont("DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, false, fontBytes, null);
            } else {
                baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
            }
        } catch (Exception e) {
            baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
        }

        Font titleFont = new Font(baseFont, 18, Font.BOLD);
        Font tableHeaderFont = new Font(baseFont, 12, Font.BOLD);
        Font tableCellFont = new Font(baseFont, 11);

        // --- Título ---
        Paragraph title = new Paragraph("Flight Fare Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // --- Tabla de vuelos ---
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 3, 2, 2, 3, 2});

        addHeaderCell(table, "Code", tableHeaderFont);
        addHeaderCell(table, "Airline", tableHeaderFont);
        addHeaderCell(table, "Origin", tableHeaderFont);
        addHeaderCell(table, "Destination", tableHeaderFont);
        addHeaderCell(table, "Date", tableHeaderFont);
        addHeaderCell(table, "Price (€)", tableHeaderFont);

        for (Flight f : flights) {
            addCell(table, f.getCode(), tableCellFont);
            addCell(table, f.getAirline(), tableCellFont);
            addCell(table, f.getOrigin(), tableCellFont);
            addCell(table, f.getDestination(), tableCellFont);
            addCell(table, f.getDate().toString(), tableCellFont);
            addCell(table, String.format("€%.2f", f.getPrice()), tableCellFont);
        }

        document.add(table);
        document.close();
    }

    private void addHeaderCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}
