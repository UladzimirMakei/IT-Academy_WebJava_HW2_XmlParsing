package by.uladzimirmakei.bankxml.validator;

import by.uladzimirmakei.bankxml.validator.impl.BankValidatorImpl;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BankValidatorTest {
    private static final String XML_FILE_PATH = "data\\banks.xml";
    private static final String XML_SCHEMA_PATH = "data\\banks.xsd";

    @Test
    public static void testXmlwithValidXsd() {
        BankValidatorImpl validator = new BankValidatorImpl();
        assertThat(validator.validate(XML_FILE_PATH, XML_SCHEMA_PATH))
                .as("Failed current XML with current XSD").isTrue();
    }

}