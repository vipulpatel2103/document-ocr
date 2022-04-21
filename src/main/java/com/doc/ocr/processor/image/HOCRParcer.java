package com.doc.ocr.processor.image;

import com.doc.ocr.processor.model.SegmentationType;
import com.doc.ocr.processor.model.TextSegment;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HOCRParcer {

    private final String HocrWord = "ocrx_word";
    private final String HocrLine = "ocr_line";
    private final String HocrBox = "bbox";
    private final String HocrClass = "class";
    private final String HocrTitle = "title";
    private final String HocrConfidence = "x_wconf";
    private int _x = 0;
    private int _y = 0;
    private double _scaleX = 1.0;
    private double _scaleY = 1.0;

    public List<TextSegment> parseHOCR(String hocr) {
        Document document = Jsoup.parse(hocr);
        Elements allElements = document.getAllElements();
        List<TextSegment> textSegmentList = new ArrayList<>();
        for (Element element : allElements) {
            if (element.hasClass(HocrWord)) {
                String title = element.attributes().get(HocrTitle);
                TextSegment textSegment = TextSegment.builder()
                        .Text(element.ownText())
                        .Type(SegmentationType.Word)
                        .Rect(getRectangleFromTitle(title))
                        .Confidence(getConfidenceFromTitle(title))
                        .build();
                textSegmentList.add(textSegment);
            }
            System.out.println(element.nodeName()
                    + " " + element.ownText());
        }
        return textSegmentList;
    }

    private int getConfidenceFromTitle(String title) {
        int xconfPos = title.indexOf(HocrConfidence);

        if (xconfPos > 0) {
            return Integer.parseInt(title.substring(xconfPos + HocrConfidence.length() + 1));
        }
        return 0;
    }

    private Rectangle getRectangleFromTitle(String title) {
        int bboxPos = title.indexOf(HocrBox);

        if (bboxPos >= 0) {
            title = title.substring(bboxPos + 4).trim();

            String[] parameters = title.split(";");

            String rectdata = parameters[0];

            String[] data = rectdata.split(" ");

            int x1 = Integer.parseInt(data[0]);
            int y1 = Integer.parseInt(data[1]);
            int x2 = Integer.parseInt(data[2]);
            int y2 = Integer.parseInt(data[3]);

            return new Rectangle(x1, y1, x2 - x1, y2 - y1);

        }
        return null;
    }

}
