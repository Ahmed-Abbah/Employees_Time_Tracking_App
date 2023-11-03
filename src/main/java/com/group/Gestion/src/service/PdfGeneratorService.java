package com.group.Gestion.src.service;

import com.group.Gestion.src.model.Employee;
import com.group.Gestion.src.model.Pause;
import com.group.Gestion.src.model.WorkDay;
import com.group.Gestion.src.repository.EmployeeRepository;
import com.group.Gestion.src.utilities.DateTimeProvider;
import com.lowagie.text.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfGeneratorService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public PdfGeneratorService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void generateEmployeeReports(HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeRepository.findAll();


        Document document = new Document(PageSize.A4);
        Paragraph title = new Paragraph("Weekly Reports for All Employees", new Font(FontFactory.getFont(FontFactory.HELVETICA).HELVETICA, 18, Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        Paragraph exportInfo = new Paragraph("Exported at: " + DateTimeProvider.getCurrentTime() + " | " + DateTimeProvider.getCurrentDate(),
                new Font(FontFactory.getFont(FontFactory.HELVETICA).HELVETICA, 12, Font.ITALIC));
        exportInfo.setAlignment(Element.ALIGN_CENTER);

        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(title);
            document.add(exportInfo);

            for (Employee employee : employees) {
                addEmployeeReportToDocument(document, employee);
            }

            document.close(); // Close the document after adding all employee reports
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ByteArrayOutputStream generateEmployeeReports() throws IOException {
        List<Employee> employees = employeeRepository.findAll();
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, pdfStream);
            document.open();

            Paragraph title = new Paragraph("Weekly Reports for All Employees", new Font(FontFactory.getFont(FontFactory.HELVETICA).HELVETICA, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph exportInfo = new Paragraph("Exported at: " + DateTimeProvider.getCurrentTime() + " | " + DateTimeProvider.getCurrentDate(),
                    new Font(FontFactory.getFont(FontFactory.HELVETICA).HELVETICA, 12, Font.ITALIC));
            exportInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(exportInfo);

            for (Employee employee : employees) {
                addEmployeeReportToDocument(document, employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

        return pdfStream;
    }


    private void addEmployeeReportToDocument(Document document, Employee employee) throws DocumentException {
        Paragraph employeeName = new Paragraph("Weekly Report for Employee: " + employee.getFirstName() + " " + employee.getLastName(),
                new Font(FontFactory.getFont(FontFactory.HELVETICA).HELVETICA, 14, Font.BOLD));
        document.add(employeeName);

        // Create a table for the employee's work data
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        PdfPCell[] tableHeaders = {
                createHeaderCell("Day of the Week"),
                createHeaderCell("Start Time"),
                createHeaderCell("End Time"),
                createHeaderCell("Working Hours"),
                createHeaderCell("Overtime Hours"),
                createHeaderCell("Number of Breaks"),
                createHeaderCell("Break Start and End Times")
        };

        for (PdfPCell header : tableHeaders) {
            table.addCell(header);
        }

        for (WorkDay workDay : employee.getWorkDays()) {
            table.addCell(workDay.getDayOfWeekName(workDay.getDate()));
            table.addCell(workDay.getStartTime());
            table.addCell(workDay.getEndTime());
            table.addCell(workDay.getTotalWorkedTime());
            table.addCell(workDay.getTotalExtraTime());
            table.addCell(String.valueOf(workDay.getPauses().size()));

            PdfPCell breakCell = new PdfPCell();
            for (Pause pause : workDay.getPauses()) {
                breakCell.addElement(new Paragraph(pause.getStartTime() + " - " + pause.getEndTime()));
            }
            table.addCell(breakCell);
        }
        document.add(table);
    }

    private PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, new Font(FontFactory.getFont(FontFactory.HELVETICA).HELVETICA, 12, Font.BOLD)));
        cell.setBackgroundColor(new Color(192, 192, 192));
        return cell;
    }
}
