package pdf_export;

import com.thoughtworks.xstream.XStream;
import narthe.compteur_km.Course;
/**
 * Created by jbrasseur on 5/19/15.
 */
public class ObjectToXml {

    public static String serializeCourse(Course course){
        XStream xstream = new XStream();
        String xml = xstream.toXML(course);
        return xml;
    }
}
