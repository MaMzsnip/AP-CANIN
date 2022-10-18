package src.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Classe implements ISaveDatabase {

    private int id, exNumber;
    private String name;
    private List<Evaluation> evaluations;

    public Classe(int id, int exNumber, String name) {
        this.id = id;
        this.exNumber = exNumber;
        this.name = name;
        this.evaluations = new ArrayList<Evaluation>();
    }

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if (!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `classe` (`name`, `nbExs`) VALUES (?, ?)");
            preparedStatement.setString(1, this.name);
            preparedStatement.setInt(2, this.exNumber);
            preparedStatement.executeUpdate();
            this.isExistInDatabase(connection);
        }
    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM classe WHERE name = ?");
        preparedStatement.setString(1, this.name);
        ResultSet rs= preparedStatement.executeQuery();
        if(rs.next()){
            this.id = rs.getInt("id");
            return true;
        }
        return false;
    }
}
