package pl.my.library.datbase.models;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "AUTHORS")
public class Author implements BaseModel{

    public Author() {
    }

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "NAME", canBeNull = false)
    private String nameAndSurname;

    @ForeignCollectionField(eager = true) //eager - zmiana pobierania z leniwego 'lazy' na eager.
    //od razu będzie dociąga całą kolekcję, w trybie lazy będzie czekał na konkretne zapytanie
    private ForeignCollection<Book> books;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String name) {
        this.nameAndSurname = name;
    }

    public ForeignCollection<Book> getBooks() {
        return books;
    }

    public void setBooks(ForeignCollection<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + nameAndSurname + '\'' +
                ", books=" + books +
                '}';
    }
}
