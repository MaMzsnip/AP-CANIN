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
public class Evaluation implements ISaveDatabase {

    private Inscription inscription;
    private Exercise exercise;
    private float note;
    private String commentary;

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if (!isExistInDatabase(connection)){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `evaluation`(`inscription`, `exercise`, `note`, `comment`) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, this.inscription.getId());
            preparedStatement.setInt(2, this.exercise.getId());
            preparedStatement.setFloat(3, this.note);
            preparedStatement.setString(4, this.commentary);
            preparedStatement.executeUpdate();
            isExistInDatabase(connection);
        }
    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `evaluation` WHERE inscription = ? AND exercise = ? AND note = ? AND comment = ?");
        preparedStatement.setInt(1, this.inscription.getId());
        preparedStatement.setInt(2, this.exercise.getId());
        preparedStatement.setFloat(3, this.note);
        preparedStatement.setString(4, this.commentary);

        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()){
            return true;
        }
        return false;
    }
}
