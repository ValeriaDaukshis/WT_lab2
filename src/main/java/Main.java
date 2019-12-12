import DAO.DataBaseDao;

public class Main {
    public static void main(String[] args) {
        String resourceDirectory = "./src/main/resources/";
        boolean isWellFormed = new XmlDeserializer().validateXml(resourceDirectory+"books.xml", resourceDirectory+"books.xsd");
        System.out.println(isWellFormed);

        DataBaseDao dataBaseDao = new DataBaseDao();
        dataBaseDao.connect();
        dataBaseDao.close();
    }
}
