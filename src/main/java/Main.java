import Beans.Book;
import DB.DataBaseDao;
import Printer.BookPrinter;
import DB.DBController;
import Xml.XmlDeserializer;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String resourceDirectory = "./src/main/resources/";
        XmlDeserializer xmlDeserializer = new XmlDeserializer();
        boolean isWellFormed = xmlDeserializer.validateXml(resourceDirectory+"books.xml", resourceDirectory+"books.xsd");

        if(!isWellFormed){
            System.out.println(isWellFormed);
        }
        else{
            ArrayList<Book> list = xmlDeserializer.readXmlFile(resourceDirectory+"books.xml");
            DataBaseDao dataBaseDao = new DataBaseDao();
            dataBaseDao.connect();
            DBController controller = null;
            try{
                controller = new DBController(new BookPrinter());
                controller.ShowDB();
                System.out.println("********");

            }catch (SQLException ex){

            }
            controller.Insert(list);
            controller.ShowDB();
            dataBaseDao.close();
        }
    }
}
