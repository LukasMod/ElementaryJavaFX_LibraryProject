package pl.my.library.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.my.library.datbase.dao.AuthorDao;
import pl.my.library.datbase.dao.BookDao;
import pl.my.library.datbase.dao.CategoryDao;
import pl.my.library.datbase.models.Author;
import pl.my.library.datbase.models.Book;
import pl.my.library.datbase.models.Category;
import pl.my.library.utils.converters.ConverterAuthor;
import pl.my.library.utils.converters.ConverterBook;
import pl.my.library.utils.converters.ConverterCategory;
import pl.my.library.utils.exceptions.ApplicationException;

import java.util.List;

public class BookModel {

    private ObjectProperty<BookFx> bookFxObjectProperty = new SimpleObjectProperty<>(new BookFx());
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();

    public void init() throws ApplicationException {
        initAuthorList();
        initCategoryList();
    }

    private void initCategoryList() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        categoryFxObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFX(c);
            categoryFxObservableList.add(categoryFx);
        });
    }

    private void initAuthorList() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        authorFxObservableList.clear();
        authorList.forEach(c -> {
            AuthorFx authorFx = ConverterAuthor.convertToAuthorFx(c);
            authorFxObservableList.add(authorFx);
        });
    }

    public void saveBookInDatabase() throws ApplicationException {
        Book book = ConverterBook.convertToBook(this.getBookFxObjectProperty());

        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(Category.class, this.getBookFxObjectProperty().getCategoryFxObjectProperty().getId());
        book.setCategory(category);

        AuthorDao authorDao = new AuthorDao();
        Author author = authorDao.findById(Author.class, this.getBookFxObjectProperty().getAuthorFxObjectProperty().getId());
        book.setAuthor(author);

        BookDao bookDao = new BookDao();
        bookDao.createOrUpdate(book);

    }

    public BookFx getBookFxObjectProperty() {
        return bookFxObjectProperty.get();
    }

    public ObjectProperty<BookFx> bookFxObjectPropertyProperty() {
        return bookFxObjectProperty;
    }

    public void setBookFxObjectProperty(BookFx bookFxObjectProperty) {
        this.bookFxObjectProperty.set(bookFxObjectProperty);
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }
}
