package by.uladzimirmakei.bankxml.entity;

public enum BankXmlTag {
    BANKS("banks"),
    NAME("name"),
    REGISTRATION("registration"),
    BANK("bank"),
    DEPOSITS("deposits"),
    ID("id"),
    TYPE("type"),
    HOLDER("holder"),
    AMOUNT("amount"),
    PROFITABILITY("profitability"),
    OPENING("opening"),
    TERM("term"),
    DEPOSIT("deposit");

    private String value;

    BankXmlTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
