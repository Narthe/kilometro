package pdf_export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jbrasseur on 5/19/15.
 */
public class HtmlToPDF {

    public static void transform(FileInputStream htmlFile, FileInputStream cssFile, FileOutputStream outputPDF) throws IOException, DocumentException
    {
        /**
         * To add CSS see :
         * https://github.com/valentin-nasta/itext-html-css-pdf-jsf-template/blob/master/iTextHtmlCssPdfJsf/src/main/java/app/SomeBean.java
         */

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputPDF);
        document.open();

        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile css = XMLWorkerHelper.getCSS(cssFile);
        cssResolver.addCss(css);

        //HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        //Pipelines
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
                new HtmlPipeline(htmlContext,
                        new PdfWriterPipeline(document, writer)));

        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser p = new XMLParser(worker);
        p.parse(htmlFile);

//		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(htmlFile));


        document.close();
    }
}