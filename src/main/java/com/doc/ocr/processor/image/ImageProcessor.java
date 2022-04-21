package com.doc.ocr.processor.image;

import com.doc.ocr.processor.Processor;
import com.doc.ocr.processor.model.TextSegment;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


//https://guides.nyu.edu/tesseract/usage
@Component("imageProcessor")
public class ImageProcessor implements Processor {

    ITesseract instance;

    public ImageProcessor() {
        instance = new Tesseract();  // JNA Interface Mapping
        instance.setDatapath(new File(getClass().getClassLoader().getResource("tessdata").getPath()).getAbsolutePath());
        instance.setVariable("tessedit_create_hocr","1");
    }

    @Override
    public List<TextSegment> getSegments(InputStream inputStream, Map<String, String> params) {
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            String result = instance.doOCR(bufferedImage);
            return new HOCRParcer().parseHOCR(result);
            //result = result.substring(result.indexOf("\n")+1);
            /*SAXParserFactory factory = SAXParserFactory.newInstance();
            try {
                *//*Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new URL("http://www.w3.org/TR/html4/loose.dtd"));
                factory.setValidating(true);
                factory.setSchema(schema);*//*
                SAXParser saxParser = factory.newSAXParser();

                XMLDefaultHandler handler = new XMLDefaultHandler();
                saxParser.parse(result, handler);

            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }*/
            //System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
    }
}
