/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: Asset.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * File: Asset.java
 * 
 * Updated by Parth Patel
 *
 * Updated Date: 2019-12-03
 * 
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the ASSET database table.
 * 
 */
@Entity
public class Asset extends ModelBase implements Serializable {
    private static final long serialVersionUID = 1L;

    // Declare the currentValue type double
    protected double currentValue;
    // Declare the name type string
    protected String name;
    // Declare owner type Portfolio
    protected Portfolio owner;
    // Declare units type int
    protected int units;
    // Declare price type double
    protected double price;

    public Asset() {
        super();
    }

    /**
     * Created ASSET_ID column in Asset Table
     */

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSET_ID")
    public int getId() {
        return this.id;
    }

    /**
     * Get Asset Name
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Asset Name
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Asset Units
     * 
     * @return the units
     */
    public int getUnits() {
        return units;
    }

    /**
     * Set Asset units
     * 
     * @param units the units to set
     */
    public void setUnits(int units) {
        this.units = units;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Asset price
     * 
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * balance and currentValue are same thing for an asset
     * 
     * @return currentValue
     * 
     */

    @Column(name = "CURRENT_VALUE")
    public double getBalance() {
        return this.currentValue;
    }

    /**
     * Set Asset balance
     * 
     * @param currentValue
     */

    public void setBalance(double currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * Get the currentvalue of Asset
     * 
     * @return
     */
    public double currentValue() {
        return currentValue;
    }

    /**
     * make sure asset's currentValue is up-to-date in Db
     * 
     * Calculate the currentvalue before geting save to Db
     */
    @PrePersist
    public void onPersist() {
        calculateCurrentValue();
    }

    /**
     * Calculate the currentvalue before update Asset
     */
    @PreUpdate
    public void onUpdate() {
        calculateCurrentValue();
    }

    /**
     * Calculate the currentValue
     */
    protected void calculateCurrentValue() {
        setBalance(price * units);
    }

    /**
     * Many to One relationship with Portfolio Table
     * 
     * @return owner - Portfolio owner
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "OWNING_PORTFOLIO_ID")
    public Portfolio getOwner() {
        return owner;
    }

    /**
     * Set the owner of the Portfolio
     * 
     * @param owner
     */
    public void setOwner(Portfolio owner) {
        this.owner = owner;
    }

}