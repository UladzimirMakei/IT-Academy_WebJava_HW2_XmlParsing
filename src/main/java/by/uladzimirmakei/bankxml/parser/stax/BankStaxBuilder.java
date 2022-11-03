package by.uladzimirmakei.bankxml.parser.stax;

import by.uladzimirmakei.bankxml.entity.Bank;
import by.uladzimirmakei.bankxml.entity.BankXmlTag;
import by.uladzimirmakei.bankxml.entity.Deposit;
import by.uladzimirmakei.bankxml.parser.BankBuilder;
import by.uladzimirmakei.bankxml.repository.impl.BankRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankStaxBuilder implements BankBuilder {
    private static Logger logger = LogManager.getLogger();
    private BankRepository bankRepository;
    private XMLInputFactory inputFactory;

    public BankStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
        bankRepository = BankRepository.getInstance();
    }

    @Override
    public void buildListBank(String fileName) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(BankXmlTag.BANK.getValue())) {
                        Bank bank = buildBank(reader);
                        bankRepository.add(bank);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            logger.log(Level.ERROR, "STAX parser exception while parsing file {}", e.getMessage());
        }
    }

    @Override
    public BankRepository getBankRepository() {
        return bankRepository;
    }

    private Bank buildBank(XMLStreamReader reader) throws XMLStreamException {
        List<Deposit> listDeposit = new ArrayList<>();
        Bank bank = new Bank()
                .setBankName(reader.getAttributeValue(null, BankXmlTag.NAME.getValue()))
                .setCountryRegistration(reader.getAttributeValue(null,
                        BankXmlTag.REGISTRATION.getValue()));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(BankXmlTag.DEPOSITS.getValue())) {
                        listDeposit = buildListDeposit(reader);
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(BankXmlTag.BANK.getValue())) {
                        bank.setDeposits(listDeposit);
                        return bank;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <bank>");
    }

    private List<Deposit> buildListDeposit(XMLStreamReader reader) throws XMLStreamException {
        List<Deposit> resultList = new ArrayList<>();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(BankXmlTag.DEPOSIT.getValue())) {
                        Deposit deposit = buildDeposit(reader);
                        resultList.add(deposit);
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (name.equals(BankXmlTag.DEPOSITS.getValue())) {
                        return resultList;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <deposits>");
    }

    private Deposit buildDeposit(XMLStreamReader reader) throws XMLStreamException {
        Deposit deposit = new Deposit();
        long id = Long.parseLong(reader.getAttributeValue(null, BankXmlTag.ID.getValue()));
        deposit.setId(id);
        deposit.setDepositType(reader.getAttributeValue(null,
                BankXmlTag.TYPE.getValue()));
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    name = reader.getLocalName();
                    switch (BankXmlTag.valueOf(name.toUpperCase())) {
                        case HOLDER -> deposit.setHolder(getXMLText(reader));
                        case AMOUNT -> {
                            BigInteger amount = new BigInteger(getXMLText(reader));
                            deposit.setAmount(amount);
                        }
                        case PROFITABILITY -> deposit.setProfitability(getXMLText(reader));
                        case OPENING -> {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
                            LocalDate date = LocalDate.parse(getXMLText(reader), formatter);
                            deposit.setOpeningDate(date);
                        }
                        case TERM -> {
                            int term = Integer.parseInt(getXMLText(reader));
                            deposit.setTerm(term);
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    name = reader.getLocalName();
                    if (BankXmlTag.valueOf(name.toUpperCase()) == BankXmlTag.DEPOSIT) {
                        return deposit;
                    }
                }
            }
        }
        throw new XMLStreamException("Unknown element in tag <deposit>");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
