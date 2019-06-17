package ru.iportnyagin.accountservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * AccountRepositoryServiceImpl.
 * Execute db manipulations in separate transaction.
 * Alternative - explicit tx managing in jdbc.
 *
 * @author portnyagin
 */
@Service
@Slf4j
public class AccountRepositoryServiceImpl {

    @Autowired
    private Repository repository;

    @Transactional
    public void createNew(Integer id, Long addAmount) {
        Account account = new Account(id, addAmount);
        repository.save(account);
        log.debug("create account:{}", account);
    }


    @Transactional
    public void addToExist(Integer id, Long addAmount, Long toCompare) {
        log.debug("addToExist id:{} addAmount:{} toCompare{}", id, addAmount, toCompare);

        Optional<Account> accountOpt = repository.findById(id);
        if (!accountOpt.isPresent()) {
            log.error("no data findById id:{}", id);
            System.exit(1);
        }

        Account account = accountOpt.get();
        Long dbAmount = account.getAmount();

        if (toCompare != dbAmount.longValue()) {
            log.error("db and cached value are different id:{} cached:{} db:{}", id, toCompare, dbAmount);
            System.exit(1);
        }

        account.addAmount(addAmount);
        repository.save(account);
        log.debug("update account:{}", account);
    }

}
