package ru.iportnyagin.accountservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AmountHolder. Used for hold amount in cache and synchronization
 *
 * @author portnyagin
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AmountHolder {

    // just for clear logging
    private int id;
    // amount
    private long value;
    // for reduce db.existsById execute
    private boolean exists;

    public void addAmount(Long amount) {
        this.value += amount;
    }
}
