package pl.my.library.modelFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.my.library.datbase.dao.AuthorDao;
import pl.my.library.datbase.dbutils.DbManager;
import pl.my.library.datbase.models.Author;
import pl.my.library.utils.converters.ConverterAuthor;
import pl.my.library.utils.exceptions.ApplicationException;

public class AuthorModel {

    //nieco inaczej niż w CategoryModel

    //"propertka" przetrzymująca autora
    private ObjectProperty<AuthorFX> authorFXObjectProperty = new SimpleObjectProperty<>(new AuthorFX());

    //zapis do bazy danych
    public void saveAuthorInDataBase() throws ApplicationException {
        AuthorDao authorDao = new AuthorDao();
        Author author = ConverterAuthor.convertAuthorFXToAuthor(this.getAuthorFXObjectProperty());
        authorDao.createOrUpdate(author);
        DbManager.closeConnectionSource();
    }


    public AuthorFX getAuthorFXObjectProperty() {
        return authorFXObjectProperty.get();
    }

    public ObjectProperty<AuthorFX> authorFXObjectPropertyProperty() {
        return authorFXObjectProperty;
    }

    public void setAuthorFXObjectProperty(AuthorFX authorFXObjectProperty) {
        this.authorFXObjectProperty.set(authorFXObjectProperty);
    }
}
