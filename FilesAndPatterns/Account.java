package FilesAndPatterns;

public class Account {
    private String fullName;
    private String birthday;
    private String email;
    private String password;
    private boolean blocked;
    public Account(String fullName,String birthday,String email,String password){
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.blocked = false;
    }
    public Account(String fullName,String birthday,String email,String password,boolean blocked){
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.blocked = blocked;
    }
    public String getFullName(){
        return fullName;
    }
    public String getBirthday(){
        return birthday;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public boolean getBlocked(){
        return blocked;
    }
    @Override
    public String toString (){
        return fullName + "," + birthday+ "," + email+ "," + password+ "," + blocked;
    }
}
