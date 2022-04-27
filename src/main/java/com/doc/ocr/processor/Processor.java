package com.doc.ocr.processor;

import com.doc.ocr.processor.model.TextSegment;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Processor {

    public List<TextSegment> getSegments(MultipartFile multipartFile, Map<String, String> params);

    public String getText(MultipartFile multipartFile, Map<String, String> params);

}
