package com.doc.ocr.processor.image;

import com.doc.ocr.processor.Processor;
import com.doc.ocr.processor.model.TextSegment;
import org.apache.commons.io.FileUtils;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

@Component("byteDecoImageProcessor")
public class ByteDecoImageProcessor implements Processor {

    @Override
    public List<TextSegment> getSegments(MultipartFile multipartFile, Map<String, String> params) {
        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(new File(getClass().getClassLoader().getResource("tessdata").getPath()).getPath(), "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        File file = null;
        try {
            file = File.createTempFile("tempfile", ".tiff");
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PIX image = pixRead(file.getAbsolutePath());
        api.SetImage(image);
        // Get OCR result

        BytePointer outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
        return null;
    }

}
