package Maven;

public class Valute {
    private String charCode;
    private int nominal;
    private String name;
    private float value;
    public  Valute(String charCode,int nominal,String name,float value){
        this.charCode=charCode;
        this.nominal=nominal;
        this.name=name;
        this.value=value;
    }
    public String getCharCode(){
        return charCode;
    }
    //public void setCharCode(String charCode) {
      //  this.charCode = charCode;
    //}
    public int getNominal() {
        return nominal;
    }
    //public void setNominal(int nominal){
    //    this.nominal = nominal;
    //}
    public String getName(){
        return name;
    }
    //public void setName(String name) {
      //  this.name = name;
    //}
    public float getValue() {
        return value;
    }
    //public void setValue(float value){
     //   this.value = value;
    //}
    @Override
    public String toString() {
        return this.nominal + " "+this.name+" = "+this.value+" Российских рублей";
    }

}
