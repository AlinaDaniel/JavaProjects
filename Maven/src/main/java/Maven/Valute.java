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

    public int getNominal() {
        return nominal;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String toString() {
        String str = " RUBLES";
        return this.nominal + " " + this.name + " = " + this.value + str;
    }


}
