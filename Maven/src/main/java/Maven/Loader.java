package Maven;
import kong.unirest.Unirest;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import kong.unirest.Unirest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Loader {
    public List<Valute> readFile(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        List<Valute> allValutes = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        // загрузили файл в память в виде объекта документ

        NodeList nodeList = document.getElementsByTagName("Valute");
        //берем узлы с тегом Valute

        //cчитываем валюты и добавляем в список
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                allValutes.add(new Valute(getValuteInfo("CharCode", element),
                        Integer.parseInt(getValuteInfo("Nominal", element)),
                        getValuteInfo("Name", element),
                        Float.parseFloat((getValuteInfo("Value", element)).replace(",","."))));
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
    public Valute searchInfo(String line) throws ParserConfigurationException, IOException, SAXException {
        String[] info = line.split(",");
        if (info.length == 2){
            String date = info[0];
            String valuteCode = info[1];
            File result = Unirest.get("https://www.cbr.ru/scripts/XML_daily.asp")
                    .queryString("date_req", date)
                    .asFile("C:/Users/User/IdeaProjects/mavenProject/src/main/java/Maven/data.xml")
                    .getBody();
            List<Valute> valutes = readFile(new File("C:/Users/User/IdeaProjects/mavenProject/src/main/java/Maven/data.xml"));
            for (Valute val : valutes) {
                if (val.getCharCode().equals(valuteCode)){
                    return val;
                }
            }
            //System.out.println(readFile(new File("C:/Users/User/IdeaProjects/mavenProject/src/main/java/Maven/data.xml")).toString());
        }
        return null;
    }
}
