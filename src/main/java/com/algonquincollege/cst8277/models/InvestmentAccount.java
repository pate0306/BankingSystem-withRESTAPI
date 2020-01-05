/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: InvestmentAccount.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * File: InvestmentAccount.java
 * 
 * Updated by Parth Patel
 *
 * Updated Date: 2019-12-03
 * 
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Column;

/**
 * The persistent class for the ACCOUNT database table.
 * 
 */
@Entity
@DiscriminatorValue(value = "I")
public class InvestmentAccount extends AccountBase implements Serializable {
    private static final long serialVersionUID = 1L;
    // Declare portfolio type Portfolio
    private Portfolio portfolio;

    public InvestmentAccount() {
    }

    // Declare createdDate type LocalDateTime
    protected LocalDateTime createdDate;
    // Declare updatedDate type LocalDateTime
    protected LocalDateTime updatedDate;

    /**
     * Created created_date column in InvestmentAccount Table
     */
    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Set InvestmentAccount Created date
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Column updated_date created in InvestmentAccount Table
     * 
     * @return updatedDate - updated InvestmentAccount date
     */
    @Column(name = "updated_date")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set the updatedDate
     * @param updatedDate
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * uni-directional One to one relationship with Portfolio Table
     * 
     * @return portfolio - portfolio with InvestmentAccount
     * 
     */
    @OneToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    /**
     * 
     * Set Portfolio
     * 
     * @param portfolio
     */
    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

}