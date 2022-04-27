package com.doc.ocr.processor;

import com.doc.ocr.processor.model.TextSegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DocProcessorService {

    @Autowired
    Processor pdfProcessor;

    @Autowired
    Processor imageProcessor;

    public List<TextSegment> doOCRSegment(MultipartFile multipartFile) {

        String mimeType = multipartFile.getContentType();
        if (SupportedMimeTypes.contains(mimeType)) {
            if (SupportedMimeTypes.MIME_APPLICATION_PDF.mimeType.equalsIgnoreCase(mimeType)) {
                return pdfProcessor.getSegments(multipartFile, null);
            } else {
                return imageProcessor.getSegments(multipartFile, null);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String doOCRText(MultipartFile multipartFile) {

        String mimeType = multipartFile.getContentType();
        if (SupportedMimeTypes.contains(mimeType)) {
            if (SupportedMimeTypes.MIME_APPLICATION_PDF.mimeType.equalsIgnoreCase(mimeType)) {
                return pdfProcessor.getText(multipartFile, null);
            } else {
                return imageProcessor.getText(multipartFile, null);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

}
