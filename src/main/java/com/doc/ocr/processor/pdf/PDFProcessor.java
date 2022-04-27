package com.doc.ocr.processor.pdf;

import com.doc.ocr.processor.Processor;
import com.doc.ocr.processor.model.TextSegment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component("pdfProcessor")
public class PDFProcessor implements Processor {
    @Override
    public List<TextSegment> getSegments(MultipartFile multipartFile, Map<String, String> params) {
        PDDocument document = null;
        try {
            document = PDDocument.load(multipartFile.getInputStream());
            CustomPDFTextStripper printTextLocations = new CustomPDFTextStripper();
            printTextLocations.setStartPage(1);
            printTextLocations.setEndPage(document.getNumberOfPages());
            printTextLocations.getText(document);
            return printTextLocations.pdfregions;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public String getText(MultipartFile multipartFile, Map<String, String> params) {
        PDDocument document = null;
        try {
            document = PDDocument.load(multipartFile.getInputStream());
            PDFTextStripper printTextLocations = new PDFTextStripper();
            printTextLocations.setStartPage(1);
            printTextLocations.setEndPage(document.getNumberOfPages());
            return printTextLocations.getText(document);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
