package com.example.monthlystatement;

import java.io.FileOutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.itextpdf.html2pdf.HtmlConverter;

@Component
public class StatementGenerator implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "file");
        velocityEngine.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        velocityEngine.setProperty("file.resource.loader.path", "src/main/resources/templates");
        velocityEngine.init();

        try {
            Template template = velocityEngine.getTemplate("monthly_statement.vm");
            User user = new User("001", "John Doe", "john@example.com", "1234 Elm St, NY", "987654321");
            List<Transaction> transactions = Arrays.asList(
                    new Transaction("2024-02-01", "Deposit", 2000.00, 5000.00),
                    new Transaction("2024-02-05", "Withdrawal", -500.00, 4500.00)
            );
            AccountSummary accountSummary = new AccountSummary(2000.00, 500.00, 4500.00);
            Bank bank = new Bank("Bank of Example", "100 Main St, NY");

            VelocityContext context = new VelocityContext();
            context.put("user", user);
            context.put("transactions", transactions);
            context.put("accountSummary", accountSummary);
            context.put("bank", bank);

            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            String htmlContent = writer.toString();

            System.out.println("Generated HTML Content:\n" + htmlContent);

            Files.createDirectories(Paths.get("output"));

            String outputPdf = "output/Monthly_Statement.pdf";
            try (FileOutputStream fos = new FileOutputStream(outputPdf)) {
                HtmlConverter.convertToPdf(htmlContent, fos);
            }

            System.out.println("PDF Generated Successfully: " + outputPdf);
        } catch (Exception e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
