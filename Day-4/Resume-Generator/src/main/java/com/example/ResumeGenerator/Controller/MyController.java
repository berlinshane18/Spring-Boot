package com.example.ResumeGenerator.Controller;

import com.example.ResumeGenerator.Resume;
import com.example.ResumeGenerator.Service.PdfService;
import com.example.ResumeGenerator.Service.ResumePdfService;
import com.example.ResumeGenerator.Service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/resume")
public class MyController {
    private final ResumeService resumeService;
    private final PdfService pdfService;
    private final ResumePdfService resumePdfService;

    @Autowired
    public MyController(ResumeService resumeService, PdfService pdfService, ResumePdfService resumePdfService) {
        this.resumeService = resumeService;
        this.pdfService = pdfService;
        this.resumePdfService = resumePdfService;
    }

    @PostMapping("/generate-resume")
    public String generateResume(@RequestBody Resume resume) {
        Resume savedResume = resumeService.saveResume(resume);
        try {
            byte[] pdfBytes = pdfService.generateResumePdf(savedResume);
            resumePdfService.saveResumePdf(savedResume.getId(), pdfBytes);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return "Error generating PDF";
        }
        return "Resume generated and saved as PDF";
    }
}
