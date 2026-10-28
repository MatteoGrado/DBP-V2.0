package de.grado.customerservice.service;

import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OcrService
{
    public String extractText(MultipartFile file) throws IOException, InterruptedException
    {

        Path inputPdf = Files.createTempFile("input-", ".pdf");
        Path outputPdf = Files.createTempFile("output-", ".pdf");

        try {
            file.transferTo(inputPdf);
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "ocrmypdf",
                    "-l", "deu+eng",
                    "--deskew",
                    "--rotate-pages",
                    "--skip-text",
                    inputPdf.toString(),
                    outputPdf.toString()
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            boolean finished = process.waitFor(120, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                throw new IOException("OCR timeout");
            }

            if (process.exitValue() != 0) {
                throw new IOException("OCR failed");
            }

            try (PDDocument document = Loader.loadPDF(outputPdf.toFile())) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            } catch (IOException e) {
                Sentry.captureException(e);
                throw new IOException("OCR failed", e);
            }
        } finally {
            Files.deleteIfExists(inputPdf);
            Files.deleteIfExists(outputPdf);
        }
    }
}
