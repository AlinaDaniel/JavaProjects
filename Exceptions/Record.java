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
    public Record(long id,String str){
        this.id = id;
        if (str.matches(".*[^0-9].*") || str.matches(".*\\D.*")){
            this.name = str;
            this.phoneNumber ="";
        } else{
            this.phoneNumber = str;
            this.name = "";
        }
    }
    public Record(long id){
        this.id = id;
        this.phoneNumber = "";
        this.name = "";
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
