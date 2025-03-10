package com.example.ResumeGenerator;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Entity
@Table(name = "resume_pdfs")
@Data
public class ResumePdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private Blob pdfData;

    @OneToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    public ResumePdf(Blob pdfData, Resume resume) {
        this.pdfData = pdfData;
        this.resume = resume;
    }

    public ResumePdf() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getPdfData() {
        return pdfData;
    }

    public void setPdfData(Blob pdfData) {
        this.pdfData = pdfData;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
