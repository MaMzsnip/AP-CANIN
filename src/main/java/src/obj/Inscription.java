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
public class Inscription implements ISaveDatabase {

    private int id;
    private Contest contest;
    private Classe classe;
    private Driver driver;
    private Dog dog;

    public Inscription(Contest contest, Classe classe, Driver driver, Dog dog) {
        this.contest = contest;
        this.classe = classe;
        this.driver = driver;
        this.dog = dog;
    }

    @Override
    public void saveDatabase(Connection connection) throws SQLException {
        if (!this.isExistInDatabase(connection)) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `inscription`(`contest`, `classe`, `dog`, `driver`) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, this.contest.getId());
            preparedStatement.setInt(2, this.classe.getId());
            preparedStatement.setInt(3, this.dog.getFapac());
            preparedStatement.setInt(4, this.driver.getId());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            this.isExistInDatabase(connection);
        }
    }

    @Override
    public boolean isExistInDatabase(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `inscription` INNER JOIN contest ON contest.id = inscription.contest INNER JOIN classe ON classe.id = inscription.classe INNER JOIN dog ON dog.fapac = inscription.dog INNER JOIN driver ON driver.id = inscription.driver WHERE inscription.contest = ? AND inscription.classe = ? AND inscription.dog = ? AND inscription.driver = ?;");
        preparedStatement.setInt(1, this.contest.getId());
        preparedStatement.setInt(2, this.classe.getId());
        preparedStatement.setInt(3, this.dog.getFapac());
        preparedStatement.setInt(4, this.driver.getId());

        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()){
            this.id = rs.getInt("id");
            return true;
        }
        return false;
    }
}
