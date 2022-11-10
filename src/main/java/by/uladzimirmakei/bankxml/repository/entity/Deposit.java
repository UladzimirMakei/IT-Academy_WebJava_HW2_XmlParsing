package by.uladzimirmakei.bankxml.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Deposit {
    private long id;
    private String depositType;
    private String holder;
    private BigInteger amount;
    private String profitability;
    private LocalDate openingDate;
    private int term;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\nDeposit: ");
        sb.append("id = ").append(id);
        sb.append("\ndepositType = ").append(depositType);
        sb.append(", holder = ").append(holder);
        sb.append("\namount = ").append(amount);
        sb.append(", profitability = ").append(profitability);
        sb.append("\nopeningDate = ").append(openingDate);
        sb.append(", term = ").append(term);
        return sb.toString();
    }
}
