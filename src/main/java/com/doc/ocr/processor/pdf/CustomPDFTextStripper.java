package com.doc.ocr.processor.pdf;

import com.doc.ocr.processor.model.SegmentationType;
import com.doc.ocr.processor.model.TextSegment;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomPDFTextStripper extends PDFTextStripper {

    List<TextSegment> pdfregions = new ArrayList<>();
    static List<String> words = new ArrayList<>();
    private double APPROXIMATE_WIDTH_MAX_DIFFERENCE = 0.5;
    private boolean is1stChar = true;
    private boolean lineMatch;
    private int pageNo = 1;
    private double lastYVal;
    private int left = 0;
    private int top = 0;
    private int bottom = 0;
    private int width = 0;
    private float currentWordNormalCharacterWidth = 0;

    private String currentText = "";

    /**
     * Instantiate a new PDFTextStripper object.
     *
     * @throws IOException If there is an error loading the properties.
     */
    public CustomPDFTextStripper() throws IOException {
        this.setSortByPosition(true);
    }

    private TextPosition currentTextPosition;

    @Override
    protected void processTextPosition(TextPosition text) {
        String tChar = text.getUnicode();

        String REGEX = "[\\S]+";
        char c = tChar.charAt(0);
        lineMatch = matchCharLine(text);
        currentTextPosition = text;

        if ((!is1stChar) && ((Math.abs(currentTextPosition.getXDirAdj() - (left + width)) > (currentWordNormalCharacterWidth / 2)) || (currentTextPosition.getXDirAdj() - (left + width) > 0 && currentTextPosition.getXDirAdj() - (left + width) + APPROXIMATE_WIDTH_MAX_DIFFERENCE > Math.abs(text.getWidthOfSpace())) || (Math.abs((currentTextPosition.getYDirAdj() - Math.abs(currentTextPosition.getHeightDir())) - (top)) > Math.abs(bottom - top)))) {
            endWord();
        }

        Pattern pattern = Pattern.compile(REGEX);
        if ((pattern.matcher(tChar).find() && (!Character.isWhitespace(c)))) {
            if ((!is1stChar) && (lineMatch == true)) {
                appendChar(tChar);
            } else if (is1stChar == true) {
                setWordCoord(text, tChar);
            }

            if (top > (currentTextPosition.getYDirAdj() - Math.abs(currentTextPosition.getHeightDir()))) {
                top = (int) (currentTextPosition.getYDirAdj() - Math.abs(currentTextPosition.getHeightDir()));
            }
            if (bottom < (currentTextPosition.getYDirAdj())) {
                bottom = (int) currentTextPosition.getYDirAdj();
            }
            width = (int) Math.abs(left - (currentTextPosition.getXDirAdj() + Math.abs(currentTextPosition.getWidthDirAdj())));

            if (currentWordNormalCharacterWidth < Math.abs(currentTextPosition.getWidthDirAdj())) {
                currentWordNormalCharacterWidth = Math.abs(currentTextPosition.getWidthDirAdj());
            }
        } else {
            endWord();
        }
    }

    protected void appendChar(String tChar) {
        currentText = currentText + tChar;
        is1stChar = false;
    }

    protected void setWordCoord(TextPosition text, String tChar) {
        currentText = currentText + tChar;
        left = (int) text.getXDirAdj();
        top = (int) (text.getYDirAdj() - Math.abs(text.getHeightDir()));
        bottom = (int) text.getYDirAdj();
        currentWordNormalCharacterWidth = Math.abs(text.getWidthDirAdj());
        is1stChar = false;
    }

    protected boolean matchCharLine(TextPosition text) {
        double yVal = Math.round(text.getYDirAdj());
        if (yVal == lastYVal) {
            return true;
        }
        lastYVal = yVal;
        endWord();
        return false;
    }

    public void endWord() {
        if (!"".endsWith(currentText)) {
      /*boolean isArabic = LanguageUtil.IsArabic(currentText);
      if (isArabic)
        currentText = new string(currentText.Reverse().ToArray());*/
            int tempHeight = bottom - top;
            top = (int) (top - (tempHeight * 0.35f));
            bottom = (int) (bottom + (tempHeight * 0.13f));

            int height = bottom - top;

            TextSegment pdfRegion = TextSegment.builder().build();

            PointRectangle Bounds = new PointRectangle();

            Bounds.LeftTop = new Point(left, top);
            Bounds.RightTop = new Point(left + width, top);
            Bounds.RightBottom = new Point(left + width, top + height);
            Bounds.LeftBottom = new Point(left, top + height);

            pdfRegion.Rect = new Rectangle((int) left, (int) top, (int) Math.round(width), (int) Math.round(height));
            pdfRegion.Text = currentText;
            pdfRegion.Type = SegmentationType.Word;
            pdfRegion.Confidence = 100;
            pdfRegion.Angle = 0.0;
            pdfRegion.textAngle = currentTextPosition.getDir();

            pdfregions.add(pdfRegion);
        }

        is1stChar = true;
        currentText = "";
        left = 0;
        top = 0;
        width = 0;
        currentWordNormalCharacterWidth = 0;
    }


}