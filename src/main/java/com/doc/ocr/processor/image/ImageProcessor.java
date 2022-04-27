package com.doc.ocr.processor.image;

import com.doc.ocr.processor.Processor;
import com.doc.ocr.processor.model.TextSegment;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
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
            Locale defaultLocale = Locale.getDefault();

            CLibrary.INSTANCE.setlocale(CLibrary.LC_ALL, "C");
            CLibrary.INSTANCE.setlocale(CLibrary.LC_NUMERIC, "C");
            CLibrary.INSTANCE.setlocale(CLibrary.LC_CTYPE, "C");

            ITesseract instance = new Tesseract1();  // JNA Interface Mapping
            instance.setDatapath(new File(getClass().getClassLoader().getResource("tessdata").getPath()).getAbsolutePath());
            instance.setVariable("tessedit_create_hocr","1");
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            String result = instance.doOCR(bufferedImage);
            return new HOCRParcer().parseHOCR(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }
}
