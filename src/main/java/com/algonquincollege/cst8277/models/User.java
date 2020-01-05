/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: User.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */

/**
 * File: User.java
 * Updated by : Parth Patel
 * Updated Date: 2019-12-02
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the USER_ACCOUNT database table.
 * 
 */
@Entity(name = "User")
@Table(name = "USER_ACCOUNT")
public class User extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Declare the name type String
    protected String name;
    // Initialize the accounts with List type AccountBase
    protected List<AccountBase> accounts = new ArrayList<>();
    // Declare the createdDate type LocalDateTime
    protected LocalDateTime createdDate;
    // Declare the updatedDate type LocalDateTime
    protected LocalDateTime updatedDate;

    /**
     * Create the column name created_date in USER_ACCOUNT Table
     */
    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Create the column name updated_date in USER_ACCOUNT Table
     */
    @Column(name = "updated_date")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * created coloumn USER_ID in USER Table
     * 
     * @return id
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int getId() {
        return this.id;
    }

    /**
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Passing User Name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Created USER_ACCOUNT_BANKACCOUNT, It has Many to Many relationship with
     * USER_ACCOUNT and ACCOUNT Table
     * 
     * @return accounts
     */
    @JsonIgnoreProperties("owners")
    @ManyToMany(targetEntity = AccountBase.class)
    @JoinTable(name = "USER_ACCOUNT_BANKACCOUNT", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID"))
    public List<AccountBase> getAccounts() {
        return accounts;
    }

    /**
     * Set the User Account
     * 
     * @param accounts
     */
    public void setAccounts(List<AccountBase> accounts) {
        this.accounts = accounts;
    }

    /**
     * Add user Account
     * 
     * @param accountBase
     */
    public void addAccount(AccountBase accountBase) {
        getAccounts().add(accountBase);
    }
}