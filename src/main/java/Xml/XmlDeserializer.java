package Xml;

import Beans.Book;
import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.apache.log4j.Logger;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.util.ArrayList;

import org.xml.sax.SAXException;

public class XmlDeserializer implements IDeserializer {

    private static final Logger log = Logger.getLogger(XmlDeserializer.class);
    private int Id;
    private String name;
    private String author;
    private int pages;
    private int price;

    public boolean validateXml(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (SAXException ex) {
            log.error("Xml is not well formed: "+ ex.getMessage());
            return false;
        }catch (IOException ex){
            log.error("Can not find file: "+ ex.getMessage());
            return false;
        }
        return true;
    }

    public ArrayList<Book> readXmlFile(String path) {
        ArrayList<Book> bookList = new ArrayList<Book>();
        try {
            File file = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("book");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    this.Id = Integer.parseInt(eElement.getAttribute("id"));
                    this.name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    this.author = eElement.getElementsByTagName("author").item(0).getTextContent();
                    this.pages = Integer.parseInt(eElement.getElementsByTagName("pages").item(0).getTextContent());
                    this.price = Integer.parseInt(eElement.getElementsByTagName("price").item(0).getTextContent());
                    bookList.add(new Book(this.Id, this.name, this.author, this.pages, this.price));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return bookList;
    }
}
