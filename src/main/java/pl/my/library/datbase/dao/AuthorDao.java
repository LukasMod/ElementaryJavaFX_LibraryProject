package pl.my.library.datbase.dao;

import com.j256.ormlite.support.ConnectionSource;

public class AuthorDao extends CommonDao {
    public AuthorDao(ConnectionSource connectionSource) {
        super(connectionSource);
    }
}
