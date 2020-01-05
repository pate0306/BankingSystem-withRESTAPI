/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: AccountBase.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * File: AccountBase.java
 * 
 * Updated by Parth Patel
 *
 * Updated Date: 2019-12-03
 * 
 */

package com.algonquincollege.cst8277.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ChequingAccount.class, name = "C"), @Type(value = InvestmentAccount.class, name = "I"),
        @Type(value = SavingsAccount.class, name = "S") })
@Entity(name = "AccountBase")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ACCOUNT")
@DiscriminatorColumn(name = "ACCOUNT_TYPE", length = 1)
public abstract class AccountBase extends ModelBase {

    // Declared the variable balance type double
    protected double balance;
    // declared the list of owners type User
    protected List<User> owners;

    /**
     * get Account balance
     * 
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * set the Account balance
     * 
     * @param balance - Account balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * created ACCOUNT_ID column with auto created the id. It is primary key for
     * AccountBase Table
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Many to Many relationship with User table. it is bi-directional relationship
     * 
     * @return owners - Users list
     */
    @JsonIgnoreProperties("accounts")
    @ManyToMany(mappedBy = "accounts")
    public List<User> getOwners() {
        return owners;
    }

    /**
     * set the ownerList
     * 
     * @param owners - User list
     */
    public void setOwners(List<User> owners) {
        this.owners = owners;
    }

    /**
     * Add the owner
     * 
     * @param owner - owner of the Account
     */
    public void addOwner(User owner) {
        getOwners().add(owner);
    }
}