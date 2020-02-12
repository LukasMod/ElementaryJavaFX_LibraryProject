package pl.my.library.datbase.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import pl.my.library.datbase.models.Book;
import pl.my.library.utils.exceptions.ApplicationException;

import java.sql.SQLException;

public class BookDao extends CommonDao {
    public BookDao() { super(); }

//    public void deleteByColumnName(String columName, int id) throws ApplicationException, SQLException {
//        Dao<Book, Object> dao = getDao(Book.class);
//        DeleteBuilder<Book, Object> deleteBuilder = dao.deleteBuilder();
//        deleteBuilder.where().eq(columName, id);
//        dao.delete(deleteBuilder.prepare());
//    }

}