package pl.my.library.datbase.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import pl.my.library.datbase.models.Book;
import pl.my.library.utils.exceptions.ApplicationException;

import java.sql.SQLException;

public class BookDao extends CommonDao {
    public BookDao() { super(); }

    public void deleteByColumnName(String columnName, int id) throws ApplicationException, SQLException {
        Dao<Book, Object> dao = getDao(Book.class); //powołuje dao
        DeleteBuilder<Book, Object> deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columnName, id);    //budowa zapytania na deleteBuilder - usuń szukając w kolumnie po ID
        dao.delete(deleteBuilder.prepare());
    }

}