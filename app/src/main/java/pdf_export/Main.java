package pdf_export;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
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

    private final ArrayList<Course> courses;

    public Main(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void serialize() throws SAXException, TransformerException, ParserConfigurationException, IOException {
        String coursesXml = "";
        for(Course course : courses) {
            coursesXml+= ObjectToXml.serializeCourse(course) + "\n";
        }
        String dataXML = "<Courses>\n" + coursesXml + "\n</Courses>";

        String inputXSL = "./templates/template.xsl";
        String inputCSS = "./templates/style.css";
        String outputxHTML = "./output/output.xhtml";
        String outputPDF = "./output/output.pdf";

        File css = new File(inputCSS);
        if (!css.exists())
        {
            throw new FileNotFoundException();
        }

        XmlToHtml st = new XmlToHtml();
        st.transform(dataXML, inputXSL, outputxHTML);
    }

}
