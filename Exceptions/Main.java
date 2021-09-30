package Exceptions;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //      Record rec1 = new Record(123,"9639630001","AD");
//        Record rec2 = new Record(113,"9639630002","AT");
//        Record rec3 = new Record(133,"9639630003","AZ");
        ArrayList<Record> records = new ArrayList();
        records.add(new Record(123, "9639630001", "AD"));
        records.add(new Record(113, "9639630001", "AT"));
        records.add(new Record(133, "9639630003", "AZ"));
        PhoneBook phoneBook = new PhoneBook();
        for (Record rec : records) {
            try {
                phoneBook.createRecord(rec);
            } catch (PhoneNumberAlreadyExists e) {
                System.out.println(rec +" не добавлена; в справочнике уже есть запись с номером телефона " + rec.getPhoneNumber());
            }
        }
        System.out.println(phoneBook.getAllRecords());
// вызов непроверяемого исключения
//        phoneBook.deleteRecord(111);
//        System.out.println(phoneBook.getAllRecords());
        phoneBook.deleteRecord(133);
        System.out.println(phoneBook.getAllRecords());

        try {
// вызов непроверяемого исключения
//            phoneBook.updateRecord(new Record(113, "9639630001", "AT"));
//            phoneBook.updateRecord(new Record(123,"9639630001", "AT"));
            phoneBook.updateRecord(new Record(123, null,null));
        } catch (RecordNotValid e) {
            System.out.println(e.getMessage()+"; данные не будут обновлены");
        }
        System.out.println(phoneBook.getAllRecords());
    }
}

