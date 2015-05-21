package pdf_export;

import com.itextpdf.text.DocumentException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import narthe.compteur_km.Course;
import narthe.compteur_km.R;

/**
 * Created by jbrasseur on 5/19/15.
 */
public class Main {


    public static File getPDF(ArrayList<Course> courses,
                                 String startDate,
                                 String endDate,
                                 Integer distance,
                                 FileInputStream inputXSL,
                                 FileInputStream inputCSS) throws SAXException, TransformerException, ParserConfigurationException, IOException, DocumentException {

        String startDateXml = String.format("<start_date>%s</start_date>\n", startDate);
        String endDateXml = String.format("<end_date>%s</end_date>\n", endDate);
        String distanceXml = String.format("<distance>%d</distance>\n", distance);
        String coursesXml = "";
        for(Course course : courses) {
            coursesXml+= ObjectToXml.serializeCourse(course) + "\n";
        }
        String dataXML = "<Courses>\n" + startDateXml + endDateXml + distanceXml + coursesXml + "\n</Courses>";

        //String inputXSL = "./templates/template.xsl";
        //String inputCSS = "./templates/style.css";
        //String outputxHTML = "./output/output.xhtml";
        //String outputPDF = "./output/output.pdf";

        XmlToHtml st = new XmlToHtml();
        File outputxHTML = File.createTempFile("output", ".xhtml");
        st.transform(dataXML, inputXSL, outputxHTML);


        // xHTML to PDF
        FileInputStream outputxHTMLStream = new FileInputStream(outputxHTML);
        File tempPDF = File.createTempFile("output", ".pdf");
        FileOutputStream outputPDF = new FileOutputStream(tempPDF);
        HtmlToPDF.transform(outputxHTMLStream, inputCSS, outputPDF);
        return tempPDF;
    }

}
