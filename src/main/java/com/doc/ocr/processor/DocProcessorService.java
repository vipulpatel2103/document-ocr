package com.doc.ocr.processor;

import com.doc.ocr.processor.model.DocumentProcessorType;
import com.doc.ocr.processor.model.TextSegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DocProcessorService {

    @Autowired
    Processor pdfProcessor;

    @Autowired
    Processor imageProcessor;

    public List<TextSegment> doOCRSegment(MultipartFile multipartFile, String lang, DocumentProcessorType type) {

        String mimeType = multipartFile.getContentType();
        if (SupportedMimeTypes.contains(mimeType)) {
            if (SupportedMimeTypes.MIME_APPLICATION_PDF.mimeType.equalsIgnoreCase(mimeType)
                    && DocumentProcessorType.pdf.equals(type)) {
                return pdfProcessor.getSegments(multipartFile, null);
            } else {
                return imageProcessor.getSegments(multipartFile, Collections.singletonMap("lang", lang));
            }
        } else {
            throw new IllegalArgumentException("Document Type not supported. Supported document type could be "+ Arrays.asList(SupportedMimeTypes.values()));
        }
    }

    public String doOCRText(MultipartFile multipartFile, String lang, DocumentProcessorType type) {

        String mimeType = multipartFile.getContentType();
        if (SupportedMimeTypes.contains(mimeType)) {
            if (SupportedMimeTypes.MIME_APPLICATION_PDF.mimeType.equalsIgnoreCase(mimeType)
                    && DocumentProcessorType.pdf.equals(type)) {
                return pdfProcessor.getText(multipartFile, null);
            } else {
                return imageProcessor.getText(multipartFile, Collections.singletonMap("lang", lang));
            }
        } else {
            throw new IllegalArgumentException("Document Type not supported. Supported document type could be "+ Arrays.asList(SupportedMimeTypes.values()).toString());
        }
    }

}
