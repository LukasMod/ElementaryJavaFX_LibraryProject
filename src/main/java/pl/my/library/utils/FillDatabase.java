package pl.my.library.utils;

import pl.my.library.datbase.dao.BookDao;
import pl.my.library.datbase.dao.CategoryDao;
import pl.my.library.datbase.dbutils.DbManager;
import pl.my.library.datbase.models.Author;
import pl.my.library.datbase.models.Book;
import pl.my.library.datbase.models.Category;
import pl.my.library.utils.exceptions.ApplicationException;

import java.util.Date;


public class FillDatabase {
    public static void fillDatabase() {
        Category category1 = new Category();
        category1.setName("Dramat");
        Author author1 = new Author();
        author1.setName("William");
        author1.setSurname("Szekspir");
        Book book1 = new Book();
        book1.setCategory(category1);
        book1.setAuthor(author1);
        book1.setTitle("Makbet");
        book1.setIsbn("123");
        book1.setRating(4);
        book1.setReleaseDate(new Date());
        book1.setAddedDate(new Date());
        book1.setDescription("Super");


        Category category2 = new Category();
        category2.setName("Sensacja");
        CategoryDao categoryDao = new CategoryDao();
        try {
            categoryDao.createOrUpdate(category2);
            DbManager.closeConnectionSource();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }


        Category category3 = new Category();
        category3.setName("Reportaż");
        Author author2 = new Author();
        author2.setName("Mariusz");
        author2.setSurname("Szczygieł");
        Book book2 = new Book();
        book2.setCategory(category3);
        book2.setAuthor(author2);
        book2.setTitle("Gottland");
        book2.setIsbn("1234");
        book2.setRating(5);
        book2.setReleaseDate(new Date());
        book2.setAddedDate(new Date());
        book2.setDescription("Jeszcze lepsze");

        Category category4 = new Category();
        category4.setName("Fantastyka");
        Author author3 = new Author();
        author3.setName("John Ronald Reuel");
        author3.setSurname("Tolkien");
        Book book3 = new Book();
        book3.setCategory(category4);
        book3.setAuthor(author3);
        book3.setTitle("Władca Pierścieni");
        book3.setIsbn("12345");
        book3.setRating(5);
        book3.setReleaseDate(new Date());
        book3.setAddedDate(new Date());
        book3.setDescription("Najlepsze");

        Author author4 = new Author();
        author4.setName("Terry ");
        author4.setSurname("Pratchett");
        Book book4 = new Book();
        book4.setCategory(category4);
        book4.setAuthor(author3);
        book4.setTitle("Kolor magii");
        book4.setIsbn("123456");
        book4.setRating(3);
        book4.setReleaseDate(new Date());
        book4.setAddedDate(new Date());
        book4.setDescription("Słabe");

        BookDao bookDao = new BookDao();
        try {
            bookDao.createOrUpdate(book1);
            bookDao.createOrUpdate(book2);
            bookDao.createOrUpdate(book3);
            bookDao.createOrUpdate(book4);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        DbManager.closeConnectionSource();
    }
}