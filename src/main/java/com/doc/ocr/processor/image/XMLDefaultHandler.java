package com.doc.ocr.processor.image;

import com.doc.ocr.processor.model.TextSegment;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLDefaultHandler extends DefaultHandler {
    private StringBuilder currentValue = new StringBuilder();
    public List<TextSegment> textSegmentList = new ArrayList<>();
    private TextSegment textSegment = TextSegment.builder().build();
    private final String HocrWord = "ocrx_word";
    private final String HocrLine = "ocr_line";
    private final String HocrBox = "bbox";
    private final String HocrClass = "class";
    private final String HocrTitle = "title";
    private final String HocrConfidence = "x_wconf";
    private boolean startWord = false;

    @Override
    public void startDocument() {
        System.out.println("Start Document");
    }

    @Override
    public void endDocument() {
        System.out.println("End Document");
    }

    @Override
    public void startElement(
            String uri,
            String localName,
            String qName,
            Attributes attributes) {

        // reset the tag value
        currentValue.setLength(0);

        System.out.printf("Start Element : %s%n", qName);

        if (qName.equalsIgnoreCase("span")
                && StringUtils.isNoneBlank(attributes.getValue("class"))
                && attributes.getValue("class").equalsIgnoreCase(HocrWord)) {
            // get tag's attribute by name
            startWord = true;
        }

    }

    @Override
    public void endElement(String uri,
                           String localName,
                           String qName) {

        System.out.printf("End Element : %s%n", qName);

        if (startWord) {
            // get tag's attribute by name
            startWord = false;
        }

    }

    // http://www.saxproject.org/apidoc/org/xml/sax/ContentHandler.html#characters%28char%5B%5D,%20int,%20int%29
    // SAX parsers may return all contiguous character data in a single chunk,
    // or they may split it into several chunks
    @Override
    public void characters(char ch[], int start, int length) {

        // The characters() method can be called multiple times for a single text node.
        // Some values may missing if assign to a new string

        // avoid doing this
        // value = new String(ch, start, length);

        // better append it, works for single or multiple calls
        currentValue.append(ch, start, length);

    }
}
