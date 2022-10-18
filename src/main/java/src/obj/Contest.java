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
public class Contest implements ISaveDatabase {

    private int id;
    private Club club;
    private Judge judge;
    private String name;
    private Date dateStart, dateEnd;

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if(!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `contest` (`judge`, `club`, `name`, `dateStart`, `dateEnd`) VALUES (?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, this.judge.getId());
            preparedStatement.setInt(2, this.club.getId());
            preparedStatement.setString(3, this.name);
            preparedStatement.setDate(4, new java.sql.Date(this.dateStart.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(this.dateEnd.getTime()));
            preparedStatement.executeUpdate();
            this.isExistInDatabase(connection);
        }
    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `contest` WHERE judge = ? AND club = ?");
        preparedStatement.setInt(1, this.judge.getId());
        preparedStatement.setInt(2, this.club.getId());
        ResultSet rs=preparedStatement.executeQuery();
        if(rs.next()){
            this.id = rs.getInt("id");
            return true;
        }
        return false;
    }
}
