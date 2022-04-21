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

    public List<TextSegment> processDocument(MultipartFile multipartFile) {

        try {
            String mimeType = multipartFile.getContentType();
            InputStream inputStream = multipartFile.getInputStream();
            if (SupportedMimeTypes.contains(mimeType)) {
                if (SupportedMimeTypes.MIME_APPLICATION_PDF.mimeType.equalsIgnoreCase(mimeType)) {
                    return pdfProcessor.getSegments(inputStream, null);
                } else {
                    return imageProcessor.getSegments(inputStream, null);
                }
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
