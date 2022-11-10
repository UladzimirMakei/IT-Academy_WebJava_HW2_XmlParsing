package by.uladzimirmakei.bankxml.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
public class Bank {
    private String bankName;
    private String countryRegistration;
    private List<Deposit> deposits;

    public Bank() {
        deposits = new ArrayList<>();
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
