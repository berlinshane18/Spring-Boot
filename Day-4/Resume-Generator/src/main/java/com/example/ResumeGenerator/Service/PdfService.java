package com.example.ResumeGenerator.Service;

import com.example.ResumeGenerator.Resume;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public byte[] generateResumePdf(Resume resume) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 20);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText(resume.getName());
            contentStream.endText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 650);
            contentStream.showText("Skills");
            contentStream.endText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            int y = 630;
            for (String skill : resume.getSkills()) {
                contentStream.beginText();
                contentStream.newLineAtOffset(120, y);
                contentStream.showText("- " + skill);
                contentStream.endText();
                y -= 20;
            }

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y - 20);
            contentStream.showText("Education");
            contentStream.endText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            y -= 40;
            contentStream.beginText();
            contentStream.newLineAtOffset(120, y);
            contentStream.showText("Degree: " + resume.getEducation().getDegree());
            contentStream.endText();
            y -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(120, y);
            contentStream.showText("Institution: " + resume.getEducation().getInstitution());
            contentStream.endText();
            y -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(120, y);
            contentStream.showText("Year: " + resume.getEducation().getYear());
            contentStream.endText();
            y -= 40;

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, y);
            contentStream.showText("Contact");
            contentStream.endText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            y -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(120, y);
            contentStream.showText("Email: " + resume.getContact().getEmail());
            contentStream.endText();
            y -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(120, y);
            contentStream.showText("Phone: " + resume.getContact().getPhone());
            contentStream.endText();
            y -= 20;
            contentStream.beginText();
            contentStream.newLineAtOffset(120, y);
            contentStream.showText("Address: " + resume.getContact().getAddress());
            contentStream.endText();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        document.close();
        try (FileOutputStream fos = new FileOutputStream("resume.pdf")) {
            fos.write(outputStream.toByteArray());
        }
        return outputStream.toByteArray();
    }
}
