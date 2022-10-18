package src.obj;

import java.sql.Connection;
import java.sql.SQLException;

public interface ISaveDatabase {

    void saveDatabase(Connection connection) throws SQLException;
    boolean isExistInDatabase(Connection connection) throws SQLException;

}
