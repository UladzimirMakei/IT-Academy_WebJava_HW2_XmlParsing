package by.uladzimirmakei.bankxml.entity;

import java.math.BigInteger;
import java.time.LocalDate;

public class Deposit {
    private long id;
    private String depositType;
    private String holder;
    private BigInteger amount;
    private String profitability;
    private LocalDate openingDate;

    private int term;

    public Deposit() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getProfitability() {
        return profitability;
    }

    public void setProfitability(String profitability) {
        this.profitability = profitability;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

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
