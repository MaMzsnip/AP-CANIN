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
public class Exercise implements ISaveDatabase {

    private int id, coefficient;
    private Classe classe;
    private String name;
    private List<Evaluation> evaluations;

    public Exercise(int id, int coefficient, Classe classe, String name) {
        this.id = id;
        this.coefficient = coefficient;
        this.classe = classe;
        this.name = name;
        this.evaluations = new ArrayList<Evaluation>();
    }

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if (!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `exercise`(`classe`, `name`, `coefficient`) VALUES (?, ?, ?)");
            preparedStatement.setInt(1, this.classe.getId());
            preparedStatement.setString(2, this.name);
            preparedStatement.setInt(3, this.coefficient);
            preparedStatement.executeUpdate();
            isExistInDatabase(connection);
        }
    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `exercise` WHERE classe = ? AND name = ? AND coefficient = ?");
        preparedStatement.setInt(1, this.classe.getId());
        preparedStatement.setString(2, this.name);
        preparedStatement.setInt(3, this.coefficient);

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            this.id = rs.getInt("id");
            return true;
        }
        return false;
    }
}
