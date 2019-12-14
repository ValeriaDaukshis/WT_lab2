package Printer;

import Beans.Book;

import java.util.ArrayList;

public class BookPrinter implements IBookPrinter
{
    public void Print(ArrayList<Book> record)
    {
       for(Book b : record){
           System.out.println(b.getId() + ", " + b.getName() + ", " + b.getAuthor()+ ", " + b.getPages() + ", " + b.getPrice());
       }
    }

}
