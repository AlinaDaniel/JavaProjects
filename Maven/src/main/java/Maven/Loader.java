package Maven;

import kong.unirest.Unirest;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Loader {
    private final static String url = "jdbc:mysql://localhost:3306/valute_database";
    private final static String user = "root";
    private final static String password = "1234";

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

    public Valute searchInfo(String date, String valuteCode) throws ParserConfigurationException, IOException, SAXException {
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
            return Loader.searchInFile(date, valuteCode);
        }
        //если не нашли в базе
        return Loader.searchInFile(date, valuteCode);

    }

    public static Valute searchInFile(String date, String valuteCode) throws ParserConfigurationException,
            IOException, SAXException {
        //поиск по документу
        List<Valute> valutes = Loader.readFile(date);
        for (Valute val : valutes) {
            if (val.getCharCode().equals(valuteCode)) {
                return val;
            }
        }
        return null;
    }

    public static void addToBase(String date) throws ParserConfigurationException, IOException, SAXException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            List<Valute> valutes = Loader.readFile(date);
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
    public static String convertDate(String date){
        String[] info = date.split("-");
        if (info.length == 3){
            return info[2]+"/"+info[1]+"/"+info[0];
        } else {return null;}
    }
}
