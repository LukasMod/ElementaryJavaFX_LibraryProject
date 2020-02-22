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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListBooksModel {
    //tu dodamy książki pobrane z bazy danych
    private ObservableList<BookFx> bookFxObservableList = FXCollections.observableArrayList();
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();

    //propertki które będą przyjmowały wartość z wybranego pola z ComboBoxów
    private ObjectProperty<AuthorFx> authorFxObjectPropertyModel = new SimpleObjectProperty<>();
    private ObjectProperty<CategoryFx> categoryFxObjectPropertyModel = new SimpleObjectProperty<>();
    //tu będziemy trzymali nasze książki
    private List<BookFx> bookFxList = new ArrayList<>();

    public void init() throws ApplicationException {
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.queryForAll(Book.class);
        bookFxList.clear();
        books.forEach(book -> {
            //Chcemy osobną listę do filtrowania/usuwania filtra, stad zmiana:
            //   this.bookFxObservableList.add(ConverterBook.convertToBookFx(book));
            //na:
            this.bookFxList.add(ConverterBook.convertToBookFx(book));
        });
        this.bookFxObservableList.setAll(bookFxList); //metoda kasuje i dodaje od nowa wszystko

        //inicjalizacja comboBoxów
        initAuthors();
        initCategory();
    }


    public void filterBooksList() {
        if (getAuthorFxObjectPropertyModel() != null && getCategoryFxObjectPropertyModel() != null) {
            filterPredicate(predicateAuthor().and(predicateCategory()));
        } else if (getCategoryFxObjectPropertyModel() != null) {
            filterPredicate(predicateCategory());
        } else if (getAuthorFxObjectPropertyModel() != null) {
            filterPredicate(predicateAuthor());
        } else {
            //jesli wszystkie filtry zwolnione - pokazujemy całą listę
            this.bookFxObservableList.setAll(this.bookFxList);
        }
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
    // - predicate pracuje na bookFx i sprawdza
    // czy kazdy obiekt z bookFx ma kategorie jak kategoria wybrana w comboBoxie (sprawdza po ID)
    private Predicate<BookFx> predicateCategory() {
        return bookFx -> bookFx.getCategoryFxObjectProperty().getId() == getCategoryFxObjectPropertyModel().getId();
    }

    private Predicate<BookFx> predicateAuthor() {
        return bookFx -> bookFx.getAuthorFxObjectProperty().getId() == getAuthorFxObjectPropertyModel().getId();
    }

    //metoda pracująca na predicate'ach, ktore stworzylismy powyzej
    private void filterPredicate(Predicate<BookFx> predicate) {
        //tworzy liste książek,
        //stream -filtruje liste, i tworzy nową, w której zapisuje wszystko
        List<BookFx> newList = bookFxList.stream().filter(predicate).collect(Collectors.toList());
        //ustawiamy nową, przefiltrowana listę
        this.bookFxObservableList.setAll(newList);

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

    public void deleteBook(BookFx bookFx) throws ApplicationException {
        BookDao bookDao = new BookDao();
        bookDao.deleteById(Book.class, bookFx.getId());
        init();
    }
}
