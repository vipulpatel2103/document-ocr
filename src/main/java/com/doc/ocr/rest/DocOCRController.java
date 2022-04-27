package com.doc.ocr.rest;

import com.doc.ocr.dto.StandardResponse;
import com.doc.ocr.processor.DocProcessorService;
import com.doc.ocr.processor.model.DocumentProcessorType;
import com.doc.ocr.processor.model.TextSegment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocOCRController {

    @Autowired
    private DocProcessorService docProcessorService;

    @GetMapping(value = "/ocr/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> testOCR() {
        return ResponseEntity.ok(
                StandardResponse.builder()
                        .success(true)
                        .data("Ping Success")
                        .build()
        );
    }

    @PostMapping(value = "/ocr/segment", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StandardResponse<List<TextSegment>>> doOCRSegment(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "en") String lang, @RequestParam(defaultValue = "pdf") DocumentProcessorType type) throws IOException {
        return ResponseEntity.ok(
                StandardResponse.<List<TextSegment>>builder()
                        .success(true)
                        .data(docProcessorService.doOCRSegment(file, lang, type))
                        .build()
        );
    }

    @PostMapping(value = "/ocr/text", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StandardResponse<String>> doOCRText(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "en") String lang, @RequestParam(defaultValue = "pdf") DocumentProcessorType type) throws IOException {
        return ResponseEntity.ok(
                StandardResponse.<String>builder()
                        .success(true)
                        .data(docProcessorService.doOCRText(file, lang, type))
                        .build()
        );
    }
}
