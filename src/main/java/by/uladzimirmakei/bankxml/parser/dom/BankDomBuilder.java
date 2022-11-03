package by.uladzimirmakei.bankxml.parser.dom;

import by.uladzimirmakei.bankxml.entity.Bank;
import by.uladzimirmakei.bankxml.entity.Deposit;
import by.uladzimirmakei.bankxml.parser.BankBuilder;
import by.uladzimirmakei.bankxml.repository.impl.BankRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankDomBuilder implements BankBuilder {
    private static Logger logger = LogManager.getLogger();
    private DocumentBuilder documentBuilder;
    private BankRepository bankRepository;

    public BankDomBuilder() {
        bankRepository = BankRepository.getInstance();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "Failed to create DocumentBuilderFactory {}", e.getMessage());
        }
    }

    @Override
    public BankRepository getBankRepository() {
        return bankRepository;
    }

    @Override
    public void buildListBank(String fileName) {
        Document doc;
        try {
            doc = documentBuilder.parse(fileName);
            Element root = doc.getDocumentElement();
            NodeList bankList = root.getElementsByTagName("bank");
            for (int i = 0; i < bankList.getLength(); i++) {
                Element bankElement = (Element) bankList.item(i);
                Bank bank = buildBank(bankElement);
                bankRepository.add(bank);
            }
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "DOM parser exception while parsing bank list {}", e.getMessage());
        }
    }


    private Bank buildBank(Element bankElement) {
        return new Bank()
                .setBankName(bankElement.getAttribute("name"))
                .setCountryRegistration(bankElement.getAttribute("registration"))
                .setDeposits(buildListDeposit(bankElement));
    }

    private List<Deposit> buildListDeposit(Element bankElement) {
        Element depositsElement = (Element) bankElement.getElementsByTagName("deposits").item(0);
        List<Deposit> deposits = new ArrayList<>();
        NodeList depositList = depositsElement.getElementsByTagName("deposit");
        for (int i = 0; i < depositList.getLength(); i++) {
            Element depositElement = (Element) depositList.item(i);
            long id = Long.parseLong(depositElement.getAttribute("id"));
            BigInteger amount = new BigInteger(getElementTextContent(depositElement, "amount"));
            int term = Integer.parseInt(getElementTextContent(depositElement, "term"));
            Deposit currentDeposit = new Deposit();
            currentDeposit.setId(id);
            currentDeposit.setDepositType(depositElement.getAttribute("type"));
            currentDeposit.setHolder(getElementTextContent(depositElement, "holder"));
            currentDeposit.setAmount(amount);
            currentDeposit.setProfitability(getElementTextContent(depositElement, "profitability"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
            LocalDate date = LocalDate.parse(getElementTextContent(depositElement, "opening"), formatter);
            currentDeposit.setOpeningDate(date);
            currentDeposit.setTerm(term);
            deposits.add(currentDeposit);
        }
        return deposits;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        return node.getTextContent();
    }
}
