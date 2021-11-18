package Maven;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Objects;

public class DataBaseLoader extends Loader {
    private final static String url = "jdbc:mysql://localhost:3306/valute_database";
    private final static String user = "root";
    private final static String password = "1234";

    public static Valute searchInfo(String date, String valuteCode) throws ParserConfigurationException, IOException, SAXException {
        //ищем в базе
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from `" + date + "`;")) {
            while (resultSet.next()) {
                if (Objects.equals(resultSet.getString("charcode"), valuteCode)) {
                    return new Valute(resultSet.getString("charcode"),
                            resultSet.getInt("nominal"),
                            resultSet.getString("name"),
                            resultSet.getFloat("value"));
                }
            }

        } catch (SQLException throwables) {
            // информации на такую даду нет или проблемы с подключением
            return null;
        }
        //если не нашли в базе
        return null;

    }

    public static void addToBase(String date) throws ParserConfigurationException, IOException, SAXException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            List<Valute> valutes = FileLoader.readFile(date);
            if (valutes.size() > 0) {
                String str1 = "CREATE TABLE IF NOT EXISTS `" + date + "` (" +
                        "  `charcode` varchar(3) NOT NULL," +
                        "  `nominal` int NOT NULL," +
                        "  `name` varchar(45) NOT NULL," +
                        "  `value` decimal(65,4) NOT NULL," +
                        "  PRIMARY KEY (`charcode`)," +
                        "  UNIQUE KEY `charcode_UNIQUE` (`charcode`));";
                statement.executeUpdate(str1);
                for (Valute val : valutes) {
                    String str2 = "INSERT INTO `" + date + String.format(
                            "` (charcode, nominal, name, value) " +
                                    "VALUES ('%s', %d, '%s', ", val.getCharCode(),
                            val.getNominal(), val.getName()) + val.getValue() +
                            ") ON DUPLICATE KEY UPDATE value=" + val.getValue() + ";";
                    statement.executeUpdate(str2);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

