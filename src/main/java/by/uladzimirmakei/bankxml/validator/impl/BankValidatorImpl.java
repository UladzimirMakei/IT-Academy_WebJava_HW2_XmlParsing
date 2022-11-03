package by.uladzimirmakei.bankxml.validator.impl;

import by.uladzimirmakei.bankxml.handler.BankErrorHandler;
import by.uladzimirmakei.bankxml.validator.BankValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class BankValidatorImpl implements BankValidator {
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean validate(String fileName, String schemaName) {
        boolean result = false;
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(fileName);
            validator.setErrorHandler(new BankErrorHandler());
            validator.validate(source);
            result = true;
        } catch (SAXException | IOException e) {
            logger.log(Level.ERROR, "{} is not correct or valid {}", fileName, e.getMessage());
        }
        return result;
    }
}
