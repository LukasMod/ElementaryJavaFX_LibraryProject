package pl.my.library.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import pl.my.library.datbase.dao.BookDao;
import pl.my.library.datbase.dao.CategoryDao;
import pl.my.library.datbase.models.Book;
import pl.my.library.datbase.models.Category;
import pl.my.library.utils.converters.ConverterCategory;
import pl.my.library.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

//oddziela bazę danych od fx, będzie obsługiwać kontroller - logika MVC

public class CategoryModel {


    //wypełniamy comboBox danymi
    private ObservableList<CategoryFx> categoryFxObservableList = FXCollections.observableArrayList();
    // categoryFxObservableList będzie podłączone do ComboBox

    private ObjectProperty<CategoryFx> categoryFXObjectProperty = new SimpleObjectProperty<>();
    //Będzie przetrzymywał obecnie wybrany element w ComboBox

    private TreeItem<String> root = new TreeItem<>();
    //TreeView posiada TreeItemy


    public ObservableList<CategoryFx> getCategoryFxObservableList() {
        return categoryFxObservableList;
    }

    public void setCategoryFxObservableList(ObservableList<CategoryFx> categoryFxObservableList) {
        this.categoryFxObservableList = categoryFxObservableList;
    }

    public CategoryFx getCategoryFXObjectProperty() {
        return categoryFXObjectProperty.get();
    }

    public ObjectProperty<CategoryFx> categoryFXObjectPropertyProperty() {
        return categoryFXObjectProperty;
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }


    public void setCategoryFXObjectProperty(CategoryFx categoryFxObjectProperty) {
        this.categoryFXObjectProperty.set(categoryFxObjectProperty);
    }

    //metoda wypełniająca categoryFxObservableList
    public void init() throws ApplicationException {
        //połączenie do bazy danych
        CategoryDao categoryDao = new CategoryDao();
        //dodaj do list CategoryList  wszystkie kategore jakie są w bazie danych
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        //w pętli, po naszych kategoriach z bazy danych, musimy dodac każdą kategorię do categoryfxlist
        initCategoryList(categoryList);
        initRoot(categoryList);
    }

    //każda kategoria będzie 1 tree itemem dla tego roota
    //przesyła do LISTY listę kategorii
    private void initRoot(List<Category> categoryList) {
        this.root.getChildren().clear(); //o
        categoryList.forEach(c -> {
            //Tworzymy nowy TreeItem, który inicjowany jest nazwą kategorii
            TreeItem<String> categoryItem = new TreeItem<>(c.getName());

            //pętla dodająca książki do listy kategorii w treeView
            c.getBooks().forEach(b -> {
                categoryItem.getChildren().add(new TreeItem<>(b.getTitle()));
            });

            //dodajemy do roota item
            root.getChildren().add(categoryItem);
        });
    }

    private void initCategoryList(List<Category> categoryList) {
        this.categoryFxObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFx categoryFX = ConverterCategory.convertToCategoryFX(c);
            this.categoryFxObservableList.add(categoryFX);
        });
    }

    public void deleteCategoryByID() throws ApplicationException, SQLException {
        CategoryDao categoryDao = new CategoryDao(); //połączenie z bazą danych
        categoryDao.deleteById(Category.class, categoryFXObjectProperty.getValue().getId()); //usuwa z bazy danych
        BookDao bookDao = new BookDao();
        bookDao.deleteByColumnName(Book.CATEGORY_ID, categoryFXObjectProperty.getValue().getId());

        init(); //czyści listę i od nowa pobiera z bazy danych
    }


    public void saveCategoryInDataBase(String name) throws ApplicationException {
        //zapis do bazy danych
        //stworzenie kategorii i zapisanie jej do bazy danych

        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        category.setName(name);
        categoryDao.createOrUpdate(category);
        init();

    }

    public void updateCategoryInDataBase() throws ApplicationException {

        CategoryDao categoryDao = new CategoryDao();
        Category tempCategory = categoryDao.findById(Category.class, getCategoryFXObjectProperty().getId());
        tempCategory.setName(getCategoryFXObjectProperty().getName());
        categoryDao.createOrUpdate(tempCategory);
        init();
    }
}
