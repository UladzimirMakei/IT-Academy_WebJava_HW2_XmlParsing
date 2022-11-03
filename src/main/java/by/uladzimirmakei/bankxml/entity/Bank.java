package by.uladzimirmakei.bankxml.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bank {
    private String bankName;
    private String countryRegistration;
    private List<Deposit> deposits;

    public Bank() {
        deposits = new ArrayList<>();
    }

    public Bank setBankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public Bank setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
        return this;
    }

    public Bank setCountryRegistration(String countryRegistration) {
        this.countryRegistration = countryRegistration;
        return this;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCountryRegistration() {
        return countryRegistration;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(bankName, bank.bankName) && Objects.equals(countryRegistration, bank.countryRegistration) && Objects.equals(deposits, bank.deposits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankName, countryRegistration, deposits);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank: ");
        sb.append("\nBank name = ").append(bankName);
        sb.append("\nCountry of registration = ").append(countryRegistration);
        sb.append("\nDeposits =").append(deposits);
        sb.append("\n");
        return sb.toString();
    }
}
