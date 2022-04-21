package com.doc.ocr.processor.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFToImage {

  /*public static void main(String[] args) throws IOException, URISyntaxException {
    PDDocument document = null;
    URL dir_url = ClassLoader.getSystemResource("25380.pdf");
    File dir = new File(dir_url.toURI());
    try {
      document = PDDocument.load(dir);
      convertToImage(document);

    } finally {
      if (document != null) {
        document.close();
      }
    }
  }

  public static void convertToImage(PDDocument document) throws IOException {
    PDFRenderer pdfRenderer = new PDFRenderer(document);
    for (int page = 0; page < document.getNumberOfPages(); ++page) {
      BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
      //BufferedImage bim = pdfRenderer.renderImage(page);
      // suffix in filename will be used as the file format
      ImageIO.write(bim, "png", new File("C:\\Vipul\\Testing\\TempFile" + (page + 1) + ".png"));
      //bim.flush();
    }
    document.close();
  }*/
}
