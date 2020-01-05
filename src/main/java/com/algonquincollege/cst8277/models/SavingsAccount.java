/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: SavingsAccount.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */

/********
 * File : SavingAccount.java
 * Name: Parth Patel
 * Updated Date: 2019-12-02
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@DiscriminatorValue(value = "S")
public class SavingsAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;

    public SavingsAccount() {
        super();
    }

    // Declared the variable savingRate type double
    private double savingRate;

    /**
     * Created the Coloumn RATE in ACCOUNT Table
     * 
     * @return savingRate
     */
    @Column(name = "RATE")
    public double getSavingRate() {
        return savingRate;
    }

    /**
     * Set the Saving Rate
     * 
     * @param savingRate
     */
    public void setSavingRate(double savingRate) {
        this.savingRate = savingRate;
    }

}