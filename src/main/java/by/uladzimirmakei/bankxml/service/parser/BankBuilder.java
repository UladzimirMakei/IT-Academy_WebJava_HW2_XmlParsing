package by.uladzimirmakei.bankxml.service.parser;

import by.uladzimirmakei.bankxml.repository.impl.BankRepository;

public interface BankBuilder {
    void buildListBank(String fileName);

    BankRepository getBankRepository();
}
