package ru.iportnyagin.accountservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Account.
 *
 * @author portnyagin
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private Integer id;

    private Long amount;

}