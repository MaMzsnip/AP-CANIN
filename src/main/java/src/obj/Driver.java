package src.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@AllArgsConstructor
public class Driver implements ISaveDatabase {

    private int id;
    private String name, lastName;

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if(!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `driver` (`name`, `lastName`) VALUES (?, ?);");
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.lastName);
            preparedStatement.executeUpdate();
            this.isExistInDatabase(connection);
        }
    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `driver` WHERE name = ? AND lastName = ? ");
        preparedStatement.setString(1, this.name);
        preparedStatement.setString(2, this.lastName);
        ResultSet rs= preparedStatement.executeQuery();
        if(rs.next()){
            this.id = rs.getInt("id");
            return true;
        }
        return false;
    }
}
