package pl.my.library.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import pl.my.library.datbase.dao.CategoryDao;
import pl.my.library.datbase.dbutils.DbManager;
import pl.my.library.datbase.models.Category;
import pl.my.library.utils.converters.ConverterCategory;
import pl.my.library.utils.exceptions.ApplicationException;

import java.util.List;

//oddziela bazę danych od fx, będzie obsługiwać kontroller - logika MVC

public class CategoryModel {


    //wypełniamy comboBox danymi
    private ObservableList<CategoryFX> categoryFXObservableList = FXCollections.observableArrayList();
    // categoryFXObservableList będzie podłączone do ComboBox

    private ObjectProperty<CategoryFX> categoryFXObjectProperty = new SimpleObjectProperty<>();
    //Będzie przetrzymywał obecnie wybrany element w ComboBox

    private TreeItem<String> root = new TreeItem<>();
    //TreeView posiada TreeItemy


    public ObservableList<CategoryFX> getCategoryFXObservableList() {
        return categoryFXObservableList;
    }

    public void setCategoryFXObservableList(ObservableList<CategoryFX> categoryFXObservableList) {
        this.categoryFXObservableList = categoryFXObservableList;
    }

    public CategoryFX getCategoryFXObjectProperty() {
        return categoryFXObjectProperty.get();
    }

    public ObjectProperty<CategoryFX> categoryFXObjectPropertyProperty() {
        return categoryFXObjectProperty;
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void setRoot(TreeItem<String> root) {
        this.root = root;
    }


    public void setCategoryFXObjectProperty(CategoryFX categoryFXObjectProperty) {
        this.categoryFXObjectProperty.set(categoryFXObjectProperty);
    }

    //metoda wypełniająca categoryFXObservableList
    public void init() throws ApplicationException {
        //połączenie do bazy danych
        CategoryDao categoryDao = new CategoryDao();
        //dodaj do list CategoryList  wszystkie kategore jakie są w bazie danych
        List<Category> categoryList = categoryDao.queryForAll(Category.class);
        //w pętli, po naszych kategoriach z bazy danych, musimy dodac każdą kategorię do categoryfxlist
        initCategoryList(categoryList);
        initRoot(categoryList);
        DbManager.closeConnectionSource();
    }

    //każda kategoria będzie 1 tree itemem dla tego roota
    //przesyła do LISTY listę kategorii
    private void initRoot(List<Category> categoryList) {
        this.root.getChildren().clear(); //o
        categoryList.forEach(c -> {
            //Tworzymy nowy TreeItem, który inicjowany jest nazwą kategorii
            TreeItem<String> categoryItem = new TreeItem<>(c.getName());

            //pętla dodająca książki do listy kategorii w treeView
            c.getBooks().forEach(b->{
                categoryItem.getChildren().add(new TreeItem<>(b.getTitle()));
            });

            //dodajemy do roota item
            root.getChildren().add(categoryItem);
        });
    }

    private void initCategoryList(List<Category> categoryList) {
        this.categoryFXObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFX categoryFX = ConverterCategory.convertToCategoryFX(c);
            this.categoryFXObservableList.add(categoryFX);
        });
    }

    public void deleteCategoryByID() throws ApplicationException {
        CategoryDao categoryDao = new CategoryDao(); //połączenie z bazą danych
        categoryDao.deleteById(Category.class, categoryFXObjectProperty.getValue().getId()); //usuwa z bazy danych
        DbManager.closeConnectionSource();
        init(); //czyści listę i od nowa pobiera z bazy danych
    }


    public void saveCategoryInDataBase(String name) throws ApplicationException {
        //zapis do bazy danych
        //stworzenie kategorii i zapisanie jej do bazy danych

        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        category.setName(name);
        categoryDao.createOrUpdate(category);
        DbManager.closeConnectionSource();
        init();

    }

    public void updateCategoryInDataBase() throws ApplicationException {

        CategoryDao categoryDao = new CategoryDao();
        Category tempCategory = categoryDao.findById(Category.class, getCategoryFXObjectProperty().getId());
        tempCategory.setName(getCategoryFXObjectProperty().getName());
        categoryDao.createOrUpdate(tempCategory);
        DbManager.closeConnectionSource();
        init();


    }
}
