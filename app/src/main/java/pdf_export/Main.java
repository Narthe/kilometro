package pdf_export;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.DocumentException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import narthe.compteur_km.Course;
import narthe.compteur_km.MyApplication;
import narthe.compteur_km.R;

/**
 * Created by jbrasseur on 5/19/15.
 */
public class Main {


    public static File getPDF(ArrayList<Course> courses,
                                 String startDate,
                                 String endDate,
                                 Integer distance,
                                 InputStream inputXSL,
                                 InputStream inputCSS) throws SAXException, TransformerException, ParserConfigurationException, IOException, DocumentException {

        String startDateXml = String.format("<start_date>%s</start_date>\n", startDate);
        String endDateXml = String.format("<end_date>%s</end_date>\n", endDate);
        String distanceXml = String.format("<distance>%d</distance>\n", distance);
        String coursesXml = "";
        for(Course course : courses) {
            coursesXml+= ObjectToXml.serializeCourse(course) + "\n";
        }
        String dataXML = "<Courses>\n" + startDateXml + endDateXml + distanceXml + coursesXml + "\n</Courses>";
        Log.d("coursesXML", dataXML);

        XmlToHtml st = new XmlToHtml();
        File outputxHTML = File.createTempFile("output", ".xhtml");
        st.transform(dataXML, inputXSL, outputxHTML);


        // xHTML to PDF
/*        Context context = MyApplication.getAppContext();
        File cacheDir = context.getCacheDir();
        Log.d("Cache directory", cacheDir.toString());*/
        FileInputStream outputxHTMLStream = new FileInputStream(outputxHTML);
        String path = Environment.getExternalStorageDirectory() + File.separator + "output.pdf";
        File tempPDF = new File(path);
        try {
            tempPDF.createNewFile();
            FileOutputStream outputPDF = new FileOutputStream(tempPDF);
            HtmlToPDF.transform(outputxHTMLStream, inputCSS, outputPDF);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return tempPDF;
    }

}
