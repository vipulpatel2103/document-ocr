package com.doc.ocr;

import com.doc.ocr.processor.model.Temp;
import com.doc.ocr.processor.model.TextSegment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class OcrApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcrApplication.class, args);
		TextSegment t = TextSegment.builder().build();
		Temp tt = Temp.builder().Rect(new Rectangle(1,2,3,4)).build();
		try {
			System.out.println(new ObjectMapper().writeValueAsString(tt));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
