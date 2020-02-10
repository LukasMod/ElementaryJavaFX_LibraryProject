package pl.my.library.datbase.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import pl.my.library.datbase.dbutils.DbManager;
import pl.my.library.datbase.models.BaseModel;
import pl.my.library.utils.FxmlUtils;
import pl.my.library.utils.exceptions.ApplicationException;

import java.sql.SQLException;
import java.util.List;

//import pl.ormlite.example.Model.BaseModel;

public abstract class CommonDao {

    public static final Logger LOGGER = LoggerFactory.getLogger(CommonDao.class);
    protected final ConnectionSource connectionSource;

    public CommonDao() {
        this.connectionSource = DbManager.getConnectionSource();
    }


    //do tworzenia obiektów
    public <T extends BaseModel, I> void createOrUpdate(BaseModel baseModel) throws ApplicationException {
        Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
        try {
            dao.createOrUpdate((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.create.update"));
        }
    }

    public <T extends BaseModel, I> void refresh(BaseModel baseModel) throws ApplicationException {
        Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
        try {
            dao.refresh((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.refresh"));
        }
    }


    public <T extends BaseModel, I> void delete(BaseModel baseModel) throws ApplicationException {
        Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
        try {
            dao.delete((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.delete"));
        }
    }

    public <T extends BaseModel, I> void deleteById(Class<T> cls, Integer id) throws ApplicationException {
        try {
            Dao<T, I> dao = getDao(cls);
            dao.deleteById((I) id);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.delete"));
        }
    }


    public <T extends BaseModel, I> T findById(Class<T> cls, Integer id) throws ApplicationException {
        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForId((I) id);
        } catch (SQLException | ApplicationException e) {
            LOGGER.warn(e.getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.not.found"));
        }
    }


    public <T extends BaseModel, I> List<T> queryForAll(Class<T> cls) throws ApplicationException {

        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForAll();   //zwraca listę wszystkich elementów
        } catch (SQLException | ApplicationException e) {
            LOGGER.warn(e.getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.not.found.all"));
        }
    }


    //uniwersalna metoda tworząca DAO dla wszystkich klas
    public <T extends BaseModel, I> Dao<T, I> getDao(Class<T> cls) throws ApplicationException {
        try {
            return DaoManager.createDao(connectionSource, cls);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.get.dao"));
        }
    }

    public <T extends BaseModel, I> QueryBuilder<T, I> getQueryBuilder(Class<T> cls) throws ApplicationException {
        Dao<T, I> dao = getDao(cls);
        return dao.queryBuilder();
    }


}
