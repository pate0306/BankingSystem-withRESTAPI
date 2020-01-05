/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: ModelBase.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * File : ModelBase.java
 * Updated By Parth Patel
 * 
 * Updated Date: 2019-12-03
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * Abstract class that is base for all
 * com.algonquincollege.cst8277.assignment3 @Entity classes
 */
@MappedSuperclass
public abstract class ModelBase implements Serializable {
    // Declare id with type integer
    protected int id;
    // Declare version with type version
    protected int version;
    // Declare createdDate with type LocalDateTime
    protected LocalDateTime createdDate;
    // Declare updatedDate with type LocalDateTime
    protected LocalDateTime updatedDate;

    public ModelBase() {
    }

    /**
     * Created column created_date in ModelBase
     * 
     * @return createdDate
     */
    @Column(name = "created_date")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the createdDate
     * 
     * @param createdDate
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Created column updated_date in ModelBase
     * 
     * @return updatedDate
     */
    @Column(name = "updated_date")
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Set the updatedDate
     * 
     * @param updatedDate
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Get Id
     * 
     * @return id
     */
    @Transient
    public int getId() {
        return this.id;
    }

    /**
     * Set id
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get version number
     * 
     * @return version
     */
    @Version
    public int getVersion() {
        return version;
    }

    /**
     * Set version number
     * 
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Set the CreatedDate
     */
    @PrePersist
    public void onPersist() {
        setCreatedDate(LocalDateTime.now());

    }

    /**
     * Set the UpdatedDate
     */
    @PreUpdate
    public void onUpdate() {
        setUpdatedDate(LocalDateTime.now());
    }

}