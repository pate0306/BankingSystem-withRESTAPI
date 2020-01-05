/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: Portfolio.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * Name: Parth Patel
 * Class Name : Portfolio.java
 * Updated Date: 2019-12-02
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * The persistent class for the PORTFOLIO database table.
 * 
 */
@Entity
//@NamedQuery(name="Portfolio.findAll", query="SELECT p FROM Portfolio p")
public class Portfolio extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;

    // declared the variable currentValue type double
    protected double currentValue;
    // declared the List of Assets type Asset
    protected List<Asset> assets;

    public Portfolio() {
    }

    // declared the variable createdDate type LocalDateTime
    protected LocalDateTime createdDate;
    // declared the variable updatedDate type LocalDateTime
    protected LocalDateTime updatedDate;

    /**
     * Create the column name created_date in Portfolio Table
     */
    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * set the Portfolio created Date
     * 
     * @param createdDate type LocalDateTime
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Create the column name updated_date in Portfolio Table
     */
    @Column(name = "updated_date")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * 
     * set the Portfolio updated Date
     * 
     * @param updatedDate type LocalDateTime
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * 
     * @return currentValue - CurrentValue for Portfolio
     */
    public double currentValue() {
        return currentValue;
    }

    /**
     * get the current balance
     * 
     * @return currentValue - get the updated Balance
     */
    public double getBalance() {
        return currentValue;
    }

    /**
     * set the currentBalance with using currentValue
     * 
     * @param currentValue -Updated the balance value
     */
    public void setBalance(double currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * created PORTFOLIO_ID column with auto created the id. It is primary key for
     * Portfolio Table
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PORTFOLIO_ID")
    public int getId() {
        return this.id;
    }

    /**
     * One to Many reapltiosnship with Asset table
     * 
     * @return assets - List of Assets
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Asset> getAssets() {
        return assets;
    }

    /**
     * set the List of Assets
     * 
     * @param assets - type of Asset
     */
    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    /**
     * Add asset
     * 
     * @param asset
     */
    public void addAsset(Asset asset) {
        getAssets().add(asset);
    }

}