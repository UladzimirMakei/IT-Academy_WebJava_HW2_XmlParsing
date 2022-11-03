package by.uladzimirmakei.bankxml.demo;

import by.uladzimirmakei.bankxml.parser.BankBuilder;
import by.uladzimirmakei.bankxml.parser.BankBuilderFactory;
import by.uladzimirmakei.bankxml.validator.BankValidator;
import by.uladzimirmakei.bankxml.validator.impl.BankValidatorImpl;
import by.uladzimirmakei.bankxml.writer.RepositoryWriter;
import by.uladzimirmakei.bankxml.writer.impl.BankRepositoryWriter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static Logger logger = LogManager.getLogger();
    private static final String XML_FILE_PATH = "data\\banks.xml";
    private static final String XML_SCHEMA_PATH = "data\\banks.xsd";

    public static void main(String[] args) {
        BankValidator validator = new BankValidatorImpl();
        if (validator.validate(XML_FILE_PATH, XML_SCHEMA_PATH)) {
            BankBuilder builder = BankBuilderFactory.createBuilder("dom");
            builder.buildListBank(XML_SCHEMA_PATH);
            RepositoryWriter writer = new BankRepositoryWriter();
            writer.writeToFile();
        } else {
            logger.log(Level.DEBUG, "Unable to validate file {} with schema {}", XML_FILE_PATH, XML_SCHEMA_PATH);
        }
    }
}