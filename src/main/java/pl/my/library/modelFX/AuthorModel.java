package pl.my.library.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.my.library.datbase.dao.AuthorDao;
import pl.my.library.datbase.dbutils.DbManager;
import pl.my.library.datbase.models.Author;
import pl.my.library.utils.converters.ConverterAuthor;
import pl.my.library.utils.exceptions.ApplicationException;

import java.util.List;

public class AuthorModel {

    //nieco inaczej niż w CategoryModel

    //"propertka" przetrzymująca autora
    private ObjectProperty<AuthorFx> authorFxObjectProperty = new SimpleObjectProperty<>(new AuthorFx());
    //kolejna dla ""edytowanych" autorów
    private ObjectProperty<AuthorFx> authorFxObjectPropertyEdit = new SimpleObjectProperty<>(new AuthorFx());
    //dodajemy listę dla TableView
    private ObservableList<AuthorFx> authorFxObservableList = FXCollections.observableArrayList();

    public void init() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        List<Author> authorList = authorDao.queryForAll(Author.class);
        this.authorFxObservableList.clear();
        authorList.forEach(author-> {    //każdemu authorowi przyporządkowujey AuthoraFX
            AuthorFx authorFX  = ConverterAuthor.convertToAuthorFx(author);
            this.authorFxObservableList.add(authorFX);
        });
        DbManager.closeConnectionSource();
    }



    //zapis do bazy danych
    public void saveAuthorInDataBase() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectProperty());
    }

    //zapis do bazy danych PO OPCJI EDYCJI
    public void saveAuthorEditInDataBase() throws ApplicationException {
        saveOrUpdate(this.getAuthorFxObjectPropertyEdit());
    }

    private void saveOrUpdate(AuthorFx authorFxObjectPropertyEdit) throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        Author author = ConverterAuthor.convertToAuthor(authorFxObjectPropertyEdit);
        authorDao.createOrUpdate(author);
        DbManager.closeConnectionSource();
        this.init();
    }


    public ObservableList<AuthorFx> getAuthorFxObservableList() {
        return authorFxObservableList;
    }

    public void setAuthorFxObservableList(ObservableList<AuthorFx> authorFxObservableList) {
        this.authorFxObservableList = authorFxObservableList;
    }

    public AuthorFx getAuthorFxObjectPropertyEdit() {
        return authorFxObjectPropertyEdit.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyEditProperty() {
        return authorFxObjectPropertyEdit;
    }

    public void setAuthorFxObjectPropertyEdit(AuthorFx authorFxObjectPropertyEdit) {
        this.authorFxObjectPropertyEdit.set(authorFxObjectPropertyEdit);
    }

    public AuthorFx getAuthorFxObjectProperty() {
        return authorFxObjectProperty.get();
    }

    public ObjectProperty<AuthorFx> authorFxObjectPropertyProperty() {
        return authorFxObjectProperty;
    }

    public void setAuthorFxObjectProperty(AuthorFx authorFxObjectProperty) {
        this.authorFxObjectProperty.set(authorFxObjectProperty);
    }
}
