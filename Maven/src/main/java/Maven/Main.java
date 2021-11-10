package Maven;

import java.io.IOException;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public class Main {
    public static void main(String[] args) {
        for (; ; ) {
            Loader load = new Loader();
            Scanner in = new Scanner(System.in);
            System.out.println("""
                                       
                    Введите дату в формате dd/mm/yyyy и идентификатор валюты через запятую,
                    для выхода из программы введите 'EX'
                    """);
            String line = in.nextLine();
            line = line.toUpperCase();
            line = line.replaceAll("\\s+", "");
            if (line.equals("EX")) break;
            try {
                Valute result = load.searchInfo(line);
                if (result == null) {
                    System.out.println("Данные введены некорректно, пример правильной" +
                            " команды '21/02/2019, USD'\nПопробуйте ещё раз. ");
                } else {
                    System.out.println(result);
                }
            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }

        }

    }
}
