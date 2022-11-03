package by.uladzimirmakei.bankxml.repository.impl;

import by.uladzimirmakei.bankxml.entity.Bank;
import by.uladzimirmakei.bankxml.repository.Repository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BankRepository implements Repository<Bank> {
    private static Logger logger = LogManager.getLogger();
    private List<Bank> storage;

    private BankRepository() {
        storage = new ArrayList<>();
    }

    private static class BankRepositoryHolder {
        private static final BankRepository bankRepository = new BankRepository();
    }

    public static BankRepository getInstance() {
        return BankRepositoryHolder.bankRepository;
    }

    @Override
    public List<Bank> getAll() {
        return storage;
    }

    @Override
    public void add(Bank newBank) {
        storage.add(newBank);
    }

    @Override
    public void update(Bank oldBank, Bank newBank) {
        if (storage.contains(oldBank)) {
            int oldBankIndex = storage.indexOf(oldBank);
            storage.set(oldBankIndex, newBank);
        } else {
            logger.log(Level.DEBUG,"Unable to update bank {}, it's not in the storage ", oldBank);
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("Repository of banks: ").append(storage).toString();
    }
}
