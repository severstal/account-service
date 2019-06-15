package ru.iportnyagin.accountservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AccountServiceImpl.
 *
 * @author portnyagin
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private Repository repository;

    @Autowired
    private Statistic statistic;

    private final Map<Integer, Long> cache = new ConcurrentHashMap<>();

    @Override
    public Long getAmount(Integer id) {

        statistic.addRead();

        return Optional.ofNullable(id)
                       .map(cache::get)
                       .orElse(0L);
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void addAmount(Integer id, Long amount) {

        if (Objects.isNull(id) && Objects.isNull(amount)) {
            return;
        }

        statistic.addWrite();

        Optional<Account> accountOpt = repository.findById(id);
        Account account = accountOpt.orElse(new Account(id, 0L));
        long newAmount = account.getAmount() + amount;
        account.setAmount(newAmount);
        repository.save(account);

        cache.put(id, newAmount);
    }

}
