package pl.my.library.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.my.library.datbase.dao.CategoryDao;
import pl.my.library.datbase.dbutils.DbManager;
import pl.my.library.datbase.models.Category;
import pl.my.library.utils.exceptions.ApplicationException;

import java.util.List;

//oddziela bazę danych od fx, będzie obsługiwać kontroller - logika MVC

public class CategoryModel {


    //wypełniamy comboBox danymi
    private ObservableList<CategoryFX> categoryFXObservableList = FXCollections.observableArrayList();
    // categoryFXObservableList będzie podłączone do ComboBox

    private ObjectProperty<CategoryFX> categoryFXObjectProperty = new SimpleObjectProperty<>();
    //Będzie przetrzymywał obecnie wybrany element w ComboBox


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
        this.categoryFXObservableList.clear();
        categoryList.forEach(c -> {
            CategoryFX categoryFX = new CategoryFX();
            categoryFX.setId(c.getId());
            categoryFX.setName(c.getName());
            this.categoryFXObservableList.add(categoryFX);
        });
        DbManager.closeConnectionSource();
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
