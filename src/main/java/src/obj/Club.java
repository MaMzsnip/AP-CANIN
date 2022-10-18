package src.obj;

import com.mysql.cj.PreparedQuery;
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
public class Club implements ISaveDatabase {

    private int id;
    private String name, address, postCode, city;

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if (!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `club` (`name`, `adress`, `postalCode`, `city`) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.address);
            preparedStatement.setString(3, this.postCode);
            preparedStatement.setString(4, this.city);
            preparedStatement.executeUpdate();
            this.isExistInDatabase(connection);
        }

    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `club` WHERE name = ?");
        preparedStatement.setString(1, this.name);
        ResultSet rs=preparedStatement.executeQuery();
        if(rs.next()){
            this.id = rs.getInt("id");
            return true;
        }
        return false;
    }
}
