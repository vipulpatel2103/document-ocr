package com.doc.ocr.processor.image;

import com.doc.ocr.processor.Processor;
import com.doc.ocr.processor.SupportedMimeTypes;
import com.doc.ocr.processor.model.TextSegment;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;


//https://guides.nyu.edu/tesseract/usage
@Component("imageProcessor")
public class ImageProcessor implements Processor {

    @Override
    public List<TextSegment> getSegments(MultipartFile multipartFile, Map<String, String> params) {
        try {
            Tesseract instance = new Tesseract();  // JNA Interface Mapping
            instance.setTessVariable("tessedit_create_hocr", "1");

            String mimeType = multipartFile.getContentType();
            String result = "";
            if (SupportedMimeTypes.MIME_APPLICATION_PDF.mimeType.equalsIgnoreCase(mimeType)) {
                File tmpFile = File.createTempFile("ocr_document", "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
                try {
                    FileUtils.writeByteArrayToFile(tmpFile, multipartFile.getBytes());
                    result = instance.doOCR(tmpFile);
                } catch (Exception e) {
                    throw e;
                } finally {
                    tmpFile.delete();
                }
            } else {
                BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
                result = instance.doOCR(bufferedImage);
            }
            return new HOCRParcer().parseHOCR(result);
        } catch (IOException | TesseractException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getText(MultipartFile multipartFile, Map<String, String> params) {
        try {
            Tesseract instance = new Tesseract();  // JNA Interface Mapping
            instance.setLanguage(params.get("lang"));
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            return instance.doOCR(bufferedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }
}
