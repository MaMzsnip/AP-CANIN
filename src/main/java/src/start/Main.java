package src.start;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.obj.*;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {


    public static void main(String[] args) {

        final String URL = "jdbc:mysql://localhost:3306/apcanin", LOGIN = "root", PASSWORD = "";

        try {

            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            FileInputStream file = new FileInputStream(new File("filesExel/cpc1.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);


            //Club
            String nameClub = sheet.getRow(5).getCell(5).toString();
            Club club = Club.builder().name(nameClub).build();
            club.saveDatabase(connection);

            System.out.println(club);

            //Dog
            String fapacDog = sheet.getRow(3).getCell(31).toString();
            String ctDog = sheet.getRow(4).getCell(24).toString();
            String sexDog = sheet.getRow(4).getCell(16).toString();
            String nameDog = sheet.getRow(3).getCell(4).toString();
            String raceDog = sheet.getRow(4).getCell(3).toString();
            String birthDogString = sheet.getRow(4).getCell(19).toString();

            int fapacDogInt = Integer.parseInt(fapacDog.split("\\.")[0]);
            int ctDogInt = Integer.parseInt(ctDog.split("\\.")[0]);
            DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            Date birthDog = dateFormat.parse(birthDogString);

            Dog dog = Dog.builder().fapac(fapacDogInt).ct(ctDogInt).club(club).sex(sexDog.charAt(0)).name(nameDog).race(raceDog).birthDate(birthDog).build();
            dog.saveDatabase(connection);

            System.out.println(dog);

            //Judge
            String allNameJudge = sheet.getRow(35).getCell(0).toString();
            String[] tabNameJudge = allNameJudge.split(" ");

            Judge judge = Judge.builder().name(tabNameJudge[0]).lastName(tabNameJudge[1]).build();
            judge.saveDatabase(connection);

            System.out.println(judge);

            // Contest
            String nameContest = sheet.getRow(0).getCell(5).toString();
            String datesContest = sheet.getRow(3).getCell(36).toString();

            String[] dates = datesContest.split(" au ");
            Date dateStart = dateFormat.parse(dates[0]);
            Date dateEnd = dateFormat.parse(dates[1]);

            Contest contest = Contest.builder().club(club).judge(judge).name(nameContest).dateStart(dateStart).dateEnd(dateEnd).build();
            contest.saveDatabase(connection);

            System.out.println(contest);

            //Driver
            String allNameDriver = sheet.getRow(8).getCell(6).toString();
            String[] tabNameDriver = allNameDriver.split("\\. ");
            tabNameDriver = tabNameDriver[1].split(" ");

            Driver driver = Driver.builder().name(tabNameDriver[0]).lastName(tabNameDriver[1]).build();
            driver.saveDatabase(connection);

            System.out.println(driver);

            //Classe
            Classe classe = Classe.builder().exNumber(9).name("Classe 1").build();
            classe.saveDatabase(connection);

            System.out.println(classe);

            //Inscription
            Inscription inscription = new Inscription(contest, classe, driver, dog);
            inscription.saveDatabase(connection);

            System.out.println(inscription);

            if(classe.getName().equals("Classe 1")) {
                for (int i = 0; i < classe.getExNumber(); i++) {
                    String nameExo = sheet.getRow(14 + i).getCell(0).toString();
                    String coeffExo = sheet.getRow(14 + i).getCell(15).toString().replace(',', '.');
                    float noteExoFloat = Float.parseFloat(sheet.getRow(14 + i).getCell(12).toString().replace(',', '.'));
                    int coeffExoInt = Integer.parseInt(coeffExo.split("\\.")[0]);
                    String commentaireExo = sheet.getRow(14 + i).getCell(21).toString();

                    Exercise exercise = Exercise.builder().classe(classe).coefficient(coeffExoInt).name(nameExo).build();
                    exercise.saveDatabase(connection);

                    Evaluation evaluation = new Evaluation(inscription, exercise, noteExoFloat, commentaireExo);
                    evaluation.saveDatabase(connection);
                    System.out.println(exercise);
                    System.out.println(evaluation);
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}