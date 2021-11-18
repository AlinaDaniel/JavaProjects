package Maven;

import kong.unirest.Unirest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader extends Loader {

    public static Valute searchInfo(String date, String valuteCode) throws ParserConfigurationException,
            IOException, SAXException {
        //поиск по документу
        List<Valute> valutes = FileLoader.readFile(date);
        for (Valute val : valutes) {
            if (val.getCharCode().equals(valuteCode)) {
                return val;
            }
        }
        return null;
    }

    public static List<Valute> readFile(String date) throws ParserConfigurationException,
            IOException, SAXException {
        List<Valute> allValutes = new ArrayList<>();
        Unirest.get("https://www.cbr.ru/scripts/XML_daily.asp")
                .queryString("date_req", date)
                .asFile("data.xml")
                .getBody();
        File xmlFile = new File("data.xml");
        // загрузили файл в память в виде объекта документ
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        //берем узлы с тегом Valute
        NodeList nodeList = document.getElementsByTagName("Valute");
        //cчитываем валюты и добавляем в список
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                allValutes.add(new Valute(getValuteInfo("CharCode", element),
                        Integer.parseInt(getValuteInfo("Nominal", element)),
                        getValuteInfo("Name", element),
                        Float.parseFloat((getValuteInfo("Value", element)
                        ).replace(",", "."))));
            }
        }
        xmlFile.delete();
        return allValutes;
    }

    public static String getValuteInfo(String tag, Element element) {
        // найти значение по тегу
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
