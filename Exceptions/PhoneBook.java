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
                throw new PhoneNumberAlreadyExists();
            }
        }
        records.add(record);
    }
    public void updateRecord(Record record,Record newRecord) throws RecordNotValid {
        boolean existRecord = false;
        long IdOfOldRecord = record.getId();
        for (Record rec : records) {
            if (rec.getId() == IdOfOldRecord) {
                existRecord = true;
                records.remove(record);
                if (newRecord.getPhoneNumber().equals("") || newRecord.getName().equals("")){
                    throw new RecordNotValid();
                }
                newRecord.setId(IdOfOldRecord);
                records.add(newRecord);
                break;
            }
        }
        if (!existRecord) {
            throw new RecordNotFound();
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
            throw new RecordNotFound();
        }
    }
}
