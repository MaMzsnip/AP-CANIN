package src.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Dog implements ISaveDatabase {

    private int fapac, ct;
    private Club club;
    private char sex;
    private String name, race;
    private Date birthDate;

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if (!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `dog` (`fapac`, `club`, `name`, `race`, `dateBirth`, `gender`, `ct`) VALUES (?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, this.fapac);
            preparedStatement.setInt(2, this.club.getId());
            preparedStatement.setString(3, this.name);
            preparedStatement.setString(4, this.race);
            preparedStatement.setDate(5, new java.sql.Date(this.birthDate.getTime()));
            preparedStatement.setString(6, String.valueOf(this.sex));
            preparedStatement.setInt(7, this.ct);
            preparedStatement.executeUpdate();
            this.isExistInDatabase(connection);
        }

    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `dog` WHERE ct = ? AND name = ? ");
        preparedStatement.setInt(1, this.ct);
        preparedStatement.setString(2, this.name);
        ResultSet rs=preparedStatement.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }
}
