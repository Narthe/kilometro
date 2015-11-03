package pdf_export;

import com.thoughtworks.xstream.XStream;
import narthe.compteur_km.Course;
/**
 * Created by jbrasseur on 5/19/15.
 */
public class ObjectToXml {

    public static String serializeCourse(Course course){
        /*XStream xstream = new XStream();
        String xml = xstream.toXML(course);
        return xml;*/

        String courseXML;
        courseXML = String.format(
                "<Course>" +
                        "<id>%d</id>" +
                        "<start>%d</start>" +
                        "<end>%d</end>" +
                        "<distance>%d</distance>" +
                        "<date>%s</date>" +
                        "</Course>", course.getId(), course.getStart(), course.getEnd(), course.getDistance(), course.getDateToString());
        return courseXML;
    }
}
