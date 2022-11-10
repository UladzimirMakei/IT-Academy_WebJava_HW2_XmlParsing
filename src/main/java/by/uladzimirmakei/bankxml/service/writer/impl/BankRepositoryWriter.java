package by.uladzimirmakei.bankxml.service.writer.impl;

import by.uladzimirmakei.bankxml.repository.entity.Bank;
import by.uladzimirmakei.bankxml.repository.impl.BankRepository;
import by.uladzimirmakei.bankxml.service.writer.RepositoryWriter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BankRepositoryWriter implements RepositoryWriter {
    private static Logger logger = LogManager.getLogger();
    private static final String RESULT_FILE_PATH = "data\\parsingResult.txt";

    @Override
    public void writeToFile() {
        logger.log(Level.DEBUG, "RepositoryWriter writing result to file {}",
                RESULT_FILE_PATH);
        BankRepository repository = BankRepository.getInstance();
        List<Bank> bankList = repository.getAll();
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(RESULT_FILE_PATH)))) {
            for (Bank array : bankList) {
                writer.println(array.toString() + " ");
            }
        } catch (IOException e) {
            logger.log(Level.ERROR,
                    "RepositoryWriter unable to write data to file {} {}",
                    RESULT_FILE_PATH, e.getMessage());
        }
    }
}
