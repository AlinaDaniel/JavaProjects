package Maven;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;


public abstract class Loader {

    public static Valute searchInfo(String date, String valuteCode) throws
            ParserConfigurationException, IOException, SAXException {
        return null;
    }

    public static String convertDate(String date) {
        String[] info = date.split("-");
        if (info.length == 3) {
            return info[2] + "/" + info[1] + "/" + info[0];
        } else {
            return null;
        }
    }
}

