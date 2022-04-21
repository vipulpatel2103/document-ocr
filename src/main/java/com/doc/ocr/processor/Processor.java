package com.doc.ocr.processor;

import com.doc.ocr.processor.model.TextSegment;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Processor {

    public List<TextSegment> getSegments(InputStream inputStream, Map<String, String> params);

}
