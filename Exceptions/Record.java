package Exceptions;

public class Record {
    private long id;
    private String phoneNumber;
    private String name;
    public Record(long id,String phone,String name){
        this.id = id;
        this.phoneNumber = phone;
        this.name = name;
    }

    public String toString(){
        return "Запись id-"+id+ " " + name+": "+ phoneNumber;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getName(){
        return name;
    }
    public long getId(){
        return id;
    }
}
