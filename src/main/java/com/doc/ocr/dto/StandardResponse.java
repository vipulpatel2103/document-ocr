package com.doc.ocr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StandardResponse<T> {
    private boolean success;
    private T data;
    private String[] errors;
}
