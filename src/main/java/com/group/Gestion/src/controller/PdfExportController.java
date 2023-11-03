package com.group.Gestion.src.controller;

import com.group.Gestion.src.service.PdfGeneratorService;
import com.group.Gestion.src.utilities.DateTimeProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class PdfExportController {
    private PdfGeneratorService pdfGeneratorService;
    @Autowired
    public PdfExportController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }
    @GetMapping("/pdf/generate")
    public void GeneratePdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        String currentTime = "["+DateTimeProvider.getCurrentTime()+"]"+"|"+"["+DateTimeProvider.getCurrentDate()+"]";

        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=pdf_" +currentTime+".pdf";
        response.setHeader(headerKey,headerValue);

        this.pdfGeneratorService.generateEmployeeReports(response);


    }


}
