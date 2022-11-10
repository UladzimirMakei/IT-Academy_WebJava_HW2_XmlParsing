package by.uladzimirmakei.bankxml.service.parser.sax;

import by.uladzimirmakei.bankxml.repository.entity.Bank;
import by.uladzimirmakei.bankxml.repository.entity.BankXmlTag;
import by.uladzimirmakei.bankxml.repository.entity.Deposit;
import by.uladzimirmakei.bankxml.repository.impl.BankRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BankSaxHandler extends DefaultHandler {
    private static Logger logger = LogManager.getLogger();
    private static final String ELEMENT_BANK = "bank";
    private static final String ELEMENT_DEPOSIT = "deposit";
    private static final String ELEMENT_DEPOSITS = "deposits";
    private BankRepository bankRepository;
    private List<Deposit> deposits;
    private BankXmlTag currentXmlTag;
    private EnumSet<BankXmlTag> withText;
    private Bank currentBank;
    private Deposit currentDeposit;

    public BankSaxHandler() {
        bankRepository = BankRepository.getInstance();
        deposits = new ArrayList<>();
        withText = EnumSet.range(BankXmlTag.HOLDER, BankXmlTag.TERM);
    }

    public BankRepository getBanks() {
        return bankRepository;
    }

    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) {
        if (ELEMENT_BANK.equals(qName)) {
            currentBank = setBankAttributes(attributes);
        } else if (ELEMENT_DEPOSIT.equals(qName)) {
            currentDeposit = setDepositAttributes(attributes);
        } else {
            BankXmlTag temp = BankXmlTag.valueOf(qName.toUpperCase());
            if (withText.contains(temp)) {
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (ELEMENT_BANK.equals(qName)) {
            bankRepository.add(currentBank);
        }
        if (ELEMENT_DEPOSIT.equals(qName)) {
            deposits.add(currentDeposit);
        }
        if (ELEMENT_DEPOSITS.equals(qName)) {
            currentBank.setDeposits(deposits);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).strip();
        if (currentXmlTag != null) {
            switch (currentXmlTag) {
                case HOLDER:
                    currentDeposit.setHolder(data);
                    break;
                case AMOUNT:
                    BigInteger amount = new BigInteger(data);
                    currentDeposit.setAmount(amount);
                    break;
                case PROFITABILITY:
                    currentDeposit.setProfitability(data);
                    break;
                case OPENING:
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("d-MM-yyyy");
                    LocalDate date = LocalDate.parse(data, formatter);
                    currentDeposit.setOpeningDate(date);
                    break;
                case TERM:
                    currentDeposit.setTerm(Integer.parseInt(data));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentXmlTag.getDeclaringClass(),
                            currentXmlTag.name());
            }
        }
        currentXmlTag = null;
    }

    private Bank setBankAttributes(Attributes attributes) {
        Bank bank = new Bank();
        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.getLocalName(i).equals("name")) {
                bank.setBankName(attributes.getValue(i));
            } else if (attributes.getLocalName(i).equals("registration")) {
                bank.setCountryRegistration(attributes.getValue(i));
            }
        }
        return bank;
    }

    private Deposit setDepositAttributes(Attributes attributes) {
        Deposit deposit = new Deposit();
        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.getLocalName(i).equals("id")) {
                long id = Long.parseLong(attributes.getValue(i));
                deposit.setId(id);
            } else if (attributes.getLocalName(i).equals("type")) {
                deposit.setDepositType(attributes.getValue(i));
            }
        }
        return deposit;
    }
}
