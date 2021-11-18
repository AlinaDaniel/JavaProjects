package Maven;
//java -jar mavenProject-1.0-SNAPSHOT-jar-with-dependencies.jar 2020-10-21 AUD
//java -jar mavenProject-1.0-SNAPSHOT-jar-with-dependencies.jar updateDB 2020-10-21
//java -jar mavenProject-1.0-SNAPSHOT-jar-with-dependencies.jar 2003-03-02 aud

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


public class Main {

    public static void main(String[] args) {
        if (args.length == 2) {
            args[0] = args[0].toUpperCase();
            args[1] = args[1].toUpperCase();
            if (args[0].equals("UPDATEDB")) {
                try {
                    DataBaseLoader.addToBase(Loader.convertDate(args[1]));
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    e.printStackTrace();
                }
            } else {
                String valuteCode = args[1];
                String date = Loader.convertDate(args[0]);
                try {
                    Valute result = DataBaseLoader.searchInfo(date, valuteCode);

                    if (result == null) {
                            result = FileLoader.searchInfo(date, valuteCode);
                        if (result == null) {

                            System.out.println("Not correct. Try again\n" +
                                    "Example: '2020-09-21 USD' or 'updateDB 2020-09-21'");
                        } else {
                            System.out.println(result);
                        }
                    } else {
                        System.out.println(result);
                    }
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Not correct. Try again\n" +
                    "Example: '2020-09-21 USD' or 'updateDB 2020-09-21'");
        }


    }
}
