package ru.iportnyagin.accountservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

/**
 * Repository.
 *
 * @author portnyagin
 */
public interface Repository extends JpaRepository<Account, Integer> {

// good approach for exists records but useless for new
//    @Override
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    Optional<Account> findById(Integer id);

}
