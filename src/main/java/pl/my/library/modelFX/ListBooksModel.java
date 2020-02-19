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
import java.util.function.Predicate;

public class ListBooksModel {
    //tu dodamy książki pobrane z bazy danych
    private ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    //propertki które będą przyjmowały wartość z wybranego pola z ComboBoxów
    private ObjectProperty<AuthorFx> authorFxObjectPropertyModel = new SimpleObjectProperty<>();
    private ObjectProperty<CategoryFx> categoryFxObjectPropertyModel = new SimpleObjectProperty<>();


    public void init() throws ApplicationException {
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.queryForAll(Book.class);
        books.forEach(book -> {
            this.bookFxObservableList.add(ConverterBook.convertToBookFx(book));
        });
        //inicjalizacja comboBoxów
        initAuthors();
        initCategory();
    }

    public void filterBooksList() {

    }

    private void initCategory() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        this.categoryFxObservableList.clear();
        categoryList.forEach(category -> {
            CategoryFx categoryFx = ConverterCategory.convertToCategoryFX(category);
            this.categoryFxObservableList.add(categoryFx);
        });
    }

    private void initAuthors() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        this.authorFxObservableList.clear();
        authorList.forEach(author -> {
            AuthorFx authorFx = ConverterAuthor.convertToAuthorFx(author);
            this.authorFxObservableList.add(authorFx);
        });
    }

    //filtrowanie
    private Predicate<BookFx> predicateCategory() {
        return bookFx -> bookFx.getCategoryFxObjectProperty().getId() == getCategoryFxObjectPropertyModel().getId();
    }
    private Predicate<BookFx> predicateAuthor() {
        return bookFx -> bookFx.getAuthorFxObjectProperty().getId() == getAuthorFxObjectPropertyModel().getId();
    }

    //metoda pracująca na predicate'ach
    private void filterPredicate(Predicate<BookFx> predicate) {

    }

    public ObservableList<BookFx> getBookFxObservableList() {
        return bookFxObservableList;
    }

    public void setBookFxObservableList(ObservableList<BookFx> bookFxObservableList) {
        this.bookFxObservableList = bookFxObservableList;
    }

    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public AuthorFx getAuthorFxObjectPropertyModel() {
        return authorFxObjectPropertyModel.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyModelProperty() {
        return authorFxObjectPropertyModel;
    }

    public void setAuthorFxObjectPropertyModel(AuthorFx authorFxObjectPropertyModel) {
        this.authorFxObjectPropertyModel.set(authorFxObjectPropertyModel);
    }

    public CategoryFx getCategoryFxObjectPropertyModel() {
        return categoryFxObjectPropertyModel.get();
    }

    public ObjectProperty<CategoryFx> categoryFxObjectPropertyModelProperty() {
        return categoryFxObjectPropertyModel;
    }

    public void setCategoryFxObjectPropertyModel(CategoryFx categoryFxObjectPropertyModel) {
        this.categoryFxObjectPropertyModel.set(categoryFxObjectPropertyModel);
    }
}
