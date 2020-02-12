package pl.my.library.datbase.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "BOOKS")

public class Book implements BaseModel {


    //bezparametrowy konstruktor Alt+Insert
    public Book() {
    }

    // TWORZYMY POLE OBCE

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "AUTHOR_ID", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Author author;

    @DatabaseField(columnName = "CATEGORY_ID", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Category category;

    //utworzy się kolumna z nazwą author_id. Lepiej nazwąć jawną naze, aby uniknąć pomyłek
    //foreignAutoCreate - pomagają w obsłudze
    //foreignAutoRefresh - pomagają w obsłudze


    @DatabaseField(columnName = "TITLE", canBeNull = false) //nigdy nie może być nullem
    private String title;

    //  @DatabaseField(columnName = "DATE_RELEASE", dataType = DataType.DATE_STRING, format = "yyyy-MM-DD")
    @DatabaseField(columnName = "RELEASE_DATE")
    private Date releaseDate;

    @DatabaseField(columnName = "ISBN",  width = 1) //unique = true,  zawsze unikalne elementy
    private long isbn;

    @DatabaseField(columnName = "DESCRIPTION", dataType = DataType.LONG_STRING) //dla Stringów powyżej 256 znaków
    private String description;

    @DatabaseField(columnName = "RATING", width = 1)
    //maksymalna ilość znaków (nie działa np. na sqlite, ale na h2 działa)
    private int rating;


    @DatabaseField(columnName = "ADDED_DATE")
    private Date addedDate;


//    @DatabaseField(columnName = "BORROWED", defaultValue = "false") //domyślna wartość
//    private boolean borrowed;
//
//    @DatabaseField(columnName = "PRICE")
//    private double price;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //    public boolean isBorrowed() {
//        return borrowed;
//    }
//
//    public void setBorrowed(boolean borrowed) {
//        this.borrowed = borrowed;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
}
