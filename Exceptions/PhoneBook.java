package Exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhoneBook {
    private ArrayList<Record> records;
    public PhoneBook(){
        records = new ArrayList<Record>();
    }
    public List<Record> getAllRecords(){
        return records;
    }
    public void createRecord(Record record) throws PhoneNumberAlreadyExists {
        for (Record rec : records) {
            if (Objects.equals(rec.getPhoneNumber(), record.getPhoneNumber())){
                throw new PhoneNumberAlreadyExists("В справочнике есть запись с таким же номером телефона");
            }
        }
        records.add(record);
    }
    public void updateRecord(Record newRecord) throws RecordNotValid {
        boolean existRecord = false;
        long IdOfRecord = newRecord.getId();
        for (Record rec : records) {
            if (rec.getId() == IdOfRecord) {
                existRecord = true;
                if (newRecord.getPhoneNumber().equals("") && newRecord.getName().equals("")){
                    throw new RecordNotValid("В новой записи не заполнено поле name и поле phoneNumber");
                } else if (newRecord.getName().equals("")) {
                    throw new RecordNotValid("В новой записи не заполнено поле name");
                } else if (newRecord.getPhoneNumber().equals("")) {
                    throw new RecordNotValid("В новой записи не заполнено поле phoneNumber");
                }
                this.deleteRecord(IdOfRecord);
                records.add(newRecord);
                break;
            }
        }
        if (!existRecord) {
            throw new RecordNotFound("Запись с таким идентификатором не существует");
        }

    }
    public void deleteRecord(long id){
        boolean existRecord = false;
        for (Record rec : records) {
            if (rec.getId() == id) {
                existRecord = true;
                records.remove(rec);
                break;
            }
        }
        if (!existRecord) {
            throw new RecordNotFound("Подходящая запись в справочнике не найдена");
        }
    }
}
