package com.doc.ocr.rest;

import com.doc.ocr.dto.StandardResponse;
import com.doc.ocr.processor.DocProcessorService;
import com.doc.ocr.processor.model.TextSegment;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.*;

@RestController
@RequestMapping("/doc")
public class DocOCRController {

    @Autowired
    private DocProcessorService docProcessorService;

    @GetMapping(value = "/ocr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> testOCR() {
        return ResponseEntity.ok(StandardResponse.builder().success(true).build());
    }

    @PostMapping(value = "/ocr", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandardResponse> doOCR(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "en") String lang) throws IOException {

        // return fileName;
        return ResponseEntity.ok(StandardResponse.builder().success(true).data(docProcessorService.processDocument(file)).build());
    }
}
