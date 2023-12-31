package ir.znu.znuproject.enums;

public enum SWITCH {
    ON("ON"),
    OFF("OFF");

    private String value;

    SWITCH(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
