package pdf_export;

/**
 * Created by jbrasseur on 5/19/15.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class XmlToHtml {

    public void transform(String dataXML, FileInputStream inputXSL, File outputxHTML) throws IOException, SAXException, ParserConfigurationException, TransformerException
    //throws TransformerConfigurationException,
    //TransformerException
    {
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(inputXSL);
        Transformer transformer = null;
        transformer = factory.newTransformer(xslStream);

        //StreamSource in = new StreamSource(dataXML);
        Document coursesNode = null;
        coursesNode = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(new InputSource(new StringReader(dataXML)));

        DOMSource sourcexml = new DOMSource(coursesNode);
        StreamResult out = new StreamResult(outputxHTML);
        //System.out.printf("The file %s exists", outputHTML);
        transformer.transform(sourcexml, out);
    }
}