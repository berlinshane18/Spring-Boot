package com.example.ResumeGenerator.Service;

import com.example.ResumeGenerator.Resume;
import com.example.ResumeGenerator.ResumePdf;
import com.example.ResumeGenerator.Service.ResumePdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

@Service
public class ResumePdfService {
    private final ResumePdfRepository resumePdfRepository;
    private final ResumeService resumeService;

    @Autowired
    public ResumePdfService(ResumePdfRepository resumePdfRepository, ResumeService resumeService) {
        this.resumePdfRepository = resumePdfRepository;
        this.resumeService = resumeService;
    }

    public void saveResumePdf(Long resumeId, byte[] pdfBytes) throws SQLException {
        Blob pdfBlob = new SerialBlob(pdfBytes);
        Resume resume = resumeService.getResumeById(resumeId);
        ResumePdf resumePdf = new ResumePdf(pdfBlob, resume);
        resumePdfRepository.save(resumePdf);
    }

    public byte[] getResumePdf(Long resumeId) throws SQLException, IOException {
        ResumePdf resumePdf = resumePdfRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found"));
        Blob pdfBlob = resumePdf.getPdfData();
        InputStream inputStream = pdfBlob.getBinaryStream();
        return inputStream.readAllBytes();
    }
}
