package by.uladzimirmakei.bankxml.parser.sax;

import by.uladzimirmakei.bankxml.handler.BankErrorHandler;
import by.uladzimirmakei.bankxml.parser.BankBuilder;
import by.uladzimirmakei.bankxml.repository.impl.BankRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class BankSaxBuilder implements BankBuilder {
    private static Logger logger = LogManager.getLogger();
    private BankSaxHandler handler = new BankSaxHandler();

    private BankRepository bankRepository;
    private XMLReader reader;

    public BankSaxBuilder() {
        bankRepository = BankRepository.getInstance();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (SAXException | ParserConfigurationException e) {
            logger.log(Level.ERROR, "Exception is caught {}", e.getMessage());
        }
        reader.setErrorHandler(new BankErrorHandler());
        reader.setContentHandler(handler);
    }

    @Override
    public void buildListBank(String fileName) {
        try {
            reader.parse(fileName);
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "SAX parser exception while parsing file {}", e.getMessage());
        }
        bankRepository = handler.getBanks();
    }

    @Override
    public BankRepository getBankRepository() {
        return bankRepository;
    }
}
