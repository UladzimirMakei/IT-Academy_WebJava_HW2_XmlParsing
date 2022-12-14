package by.uladzimirmakei.bankxml.service.parser;

import by.uladzimirmakei.bankxml.service.parser.sax.BankSaxBuilder;
import by.uladzimirmakei.bankxml.service.parser.dom.BankDomBuilder;
import by.uladzimirmakei.bankxml.service.parser.stax.BankStaxBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankBuilderFactory {
    private BankBuilderFactory() {
    }

    private enum BuilderType {
        DOM,
        SAX,
        STAX
    }

    private static Logger logger = LogManager.getLogger();

    public static BankBuilder createBuilder(String builderType) {
        BuilderType type = BuilderType.valueOf(
                builderType.toUpperCase());
        switch (type) {
            case DOM:
                return new BankDomBuilder();
            case SAX:
                return new BankSaxBuilder();
            case STAX:
                return new BankStaxBuilder();
            default:
                logger.log(Level.DEBUG,
                        "Using default DOM builder, incorrect request {}",
                        type);
                return new BankDomBuilder();
        }
    }
}
