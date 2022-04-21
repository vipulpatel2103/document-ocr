package com.doc.ocr.processor.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.awt.*;


@Builder
public class TextSegment {
    public String Text;
    public SegmentationType Type;
    public int Confidence;
    public Double Angle;
    public Float textAngle;
    public Rectangle Rect;

    @JsonGetter("Rect")
    public String getRect() {
        return new StringBuilder().append(Rect.x).append(",").append(Rect.y).append(",").append(Rect.width).append(",").append(Rect.height).toString();
    }
}
