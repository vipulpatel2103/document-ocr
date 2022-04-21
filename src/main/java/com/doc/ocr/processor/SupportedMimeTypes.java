package com.doc.ocr.processor;

import java.util.ArrayList;
import java.util.List;

public enum SupportedMimeTypes {
    MIME_APPLICATION_PDF("application/pdf"),
    MIME_IMAGE_JPEG("image/jpeg"),
    MIME_IMAGE_TIFF("image/tiff"),
    MIME_IMAGE_PNG("image/png");

    String mimeType;

    SupportedMimeTypes(String type) {
        this.mimeType = type;
    }

    public static boolean contains(String test) {
        for (SupportedMimeTypes c : SupportedMimeTypes.values()) {
            if (c.mimeType.equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }
}
