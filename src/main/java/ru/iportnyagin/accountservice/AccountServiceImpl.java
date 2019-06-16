package ru.iportnyagin.accountservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AccountServiceImpl.
 *
 * @author portnyagin
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private Repository repository;

    @Autowired
    private Statistic statistic;

    @Autowired
    private AccountService accountService;

    private final Map<Integer, AmountHolder> cache = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        List<Account> accounts = repository.findAll();
        accounts.forEach(a -> cache.put(a.getId(), new AmountHolder(a.getId(), a.getAmount(), true)));
    }

    @Override
    public Long getAmount(Integer id) {

        statistic.addRead();

        if (cache.containsKey(id)) {
            return cache.get(id).getValue();
        } else {
            return 0L;
        }
    }

    @Override
    public void addAmount(Integer id, Long addAmount) {

        statistic.addWrite();

        log.debug("addAmount id:{} amount:{}", id, addAmount);

        cache.putIfAbsent(id, new AmountHolder(id, 0, false));
        AmountHolder holder = cache.get(id);

        synchronized (holder) {
            log.debug("synchronized by holder:{}", holder);

            if (holder.isExists()) {
                accountService.addToExist(id, addAmount, holder.getValue());
            } else {
                accountService.createNew(id, addAmount);
                holder.setExists(repository.existsById(id));
            }

            holder.setValue(holder.getValue() + addAmount);
            log.debug("end synchronized by holder:{}", holder);
        }
    }

    @Override
    @Transactional
    public void createNew(Integer id, Long addAmount) {
        Account account = new Account(id, addAmount);
        repository.save(account);
        log.debug("create account:{}", account);
    }

    @Override
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

        account.setAmount(dbAmount + addAmount);
        repository.save(account);
        log.debug("update account:{}", account);
    }

}
