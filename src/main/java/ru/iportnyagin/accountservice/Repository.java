package ru.iportnyagin.accountservice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository.
 *
 * @author portnyagin
 */
public interface Repository extends JpaRepository<Account, Integer> {
}
