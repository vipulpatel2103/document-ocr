package com.doc.ocr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardResponse {
    private boolean success;
    private Object data;
    private String[] errors;
}
