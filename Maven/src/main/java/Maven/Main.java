package Maven;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class Main {
    private final static String url = "jdbc:mysql://localhost:3306/valute_database";
    private final static String user = "root";
    private final static String password = "1234";

    public static void main(String[] args) {
        for (; ; ) {
            Loader load = new Loader();
            Scanner in = new Scanner(System.in);
            System.out.println("""

                    Введите команду (например, '2020-09-21 USD' или 'updateDB 2020-09-21'),
                    для выхода из программы введите 'EX'
                    """);
            String line = in.nextLine();
            line = line.toUpperCase();
            line = line.trim();
            if (line.equals("EX")) break;
            String[] info = line.split(" ");
            if (info.length == 2) {
                if (info[0].equals("UPDATEDB")){
                    try {
                        Loader.addToBase(Loader.convertDate(info[1]));
                    } catch (ParserConfigurationException | IOException | SAXException e) {
                        e.printStackTrace();
                    }
                } else {
                    String valuteCode = info[1];
                    String date = Loader.convertDate(info[0]);
                    try {
                        Valute result = load.searchInfo(date,valuteCode);

                        if (result == null) {
                            System.out.println("Данные введены некорректно, пример правильной" +
                                    " команды: '2020-09-21 USD' или 'updateDB 2020-09-21'\nПопробуйте ещё раз. ");
                        } else {
                            System.out.println(result);
                        }
                    } catch (ParserConfigurationException | IOException | SAXException e) {
                        e.printStackTrace();
                    }
                }
            } else {System.out.println("Данные введены некорректно, пример правильной" +
                    " команды '21/02/2019 USD'\nПопробуйте ещё раз. ");}


        }


    }
}
