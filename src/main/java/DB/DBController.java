package DB;

import Beans.Book;
import Printer.BookPrinter;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.ResultSet;

public class DBController {
    private Statement statement;
    private BookPrinter printer;
    private Connection connection = DataBaseDao.connect();
    private static final Logger log = Logger.getLogger(DBController.class);

    public DBController(BookPrinter printer) {
        try{
            this.statement = this.connection.createStatement();
            this.printer = printer;
        } catch (SQLException ex){
            log.error(ex.getMessage());
        }

    }

    public void Insert(int id, String name, String author, int pages, double price){
        try {
            String str = "INSERT INTO books(id,name,author,pages,price) VALUES (" + id + ",'" + name + "','" + author+ "'," + pages + "," + price + "); ";
            this.statement.execute(str);
        } catch (SQLException ex) {
            log.warn("Error in database inserting: " + ex);
        }
    }

    public void Insert(ArrayList<Book> list){
        for (Book b : list) {
            try {
                String str = "INSERT INTO books(id,name,author,pages,price) VALUES (" + b.getId() + ",'" + b.getName() + "','" + b.getAuthor()+ "'," + b.getPages() + "," + b.getPrice() + "); ";
                this.statement.execute(str);
            } catch (SQLException ex) {
                log.warn("Error in database inserting: " + ex);
            }
        }
    }

    public void Delete(int id){
        try {
            String str = "DELETE FROM books WHERE id=" + id + ";";
            this.statement.execute(str);
        } catch (SQLException ex) {
            log.warn("Error in database inserting: " + ex);
        }
    }

    public void Delete(String name){
        try {
            String str = "DELETE FROM books WHERE name='" + name+"';";
            this.statement.execute(str);
        } catch (SQLException ex) {
            log.warn("Error in database inserting: " + ex);
        }
    }

    public void ShowDB(){
        ArrayList<Book> list = new ArrayList<Book>();
        try {
            ResultSet rs = this.statement.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                int id= rs.getInt("id");
                String name= rs.getString("name");
                String author= rs.getString("author");
                int pages= rs.getInt("pages");
                double price= rs.getDouble("price");
                list.add(new Book(id, name, author, pages, price));
            }
        } catch (SQLException ex) {
            log.warn("Error in database inserting: " + ex);
        } catch (Exception ex) {
            log.warn("Error in database inserting: " + ex);
        }
        printer.Print(list);
    }
}
