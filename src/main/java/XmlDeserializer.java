import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.apache.log4j.Logger;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlDeserializer implements IDeserializer {
    private static final Logger log = Logger.getLogger(XmlDeserializer.class);
    public boolean validateXml(String xmlPath, String xsdPath) {
        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (SAXException ex) {
            log.error("Exception: "+ex.getMessage());
            return false;
        }catch (IOException ex){
            log.error("Exception: "+ex.getMessage());
            return false;
        }
        return true;
    }
}
