package Maven;

public class Valute {
    private String charCode;
    private int nominal;
    private String name;
    private float value;

    public Valute(String charCode, int nominal, String name, float value) {
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getCharCode() {
        return charCode;
    }

    @Override
    public String toString() {
        return this.nominal + " " + this.name + " = " + this.value + " Российских рублей";
    }

}