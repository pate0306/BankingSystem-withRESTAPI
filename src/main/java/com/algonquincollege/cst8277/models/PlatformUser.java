/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: PlatformUser.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */

/**********************
 * File : PlatformUser.java
 * Updated by : Parth Patel
 * Updated Date: 2019-12-01
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.security.Principal;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */

@Entity
@Table(name = "PLATFORM_USER")
public class PlatformUser extends ModelBase implements Principal, Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    // Declared the variable username type String
    protected String username;
    // Declared the variable pwHash type String
    protected String pwHash;
    // Declared the variable platformRoles type PlatformRole
    protected Set<PlatformRole> platformRoles;
    // Declared the variable bankingUser type User
    protected User bankingUser;

    public PlatformUser() {
        super();
    }

    /**
     * Get the USER_ID
     * 
     * @return id
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int getId() {
        return id;
    }

    /**
     * Set the PlatFormUser id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get PlatFormUser name
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set PlatFormUser name
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get PlatFormUser name
     * 
     * @return pwHash (PasswordHash)
     */
    public String getPwHash() {
        return pwHash;
    }

    /**
     * Set pwHash (PasswordHash)
     * 
     * @param pwHash
     */
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    /**
     * Many to Many relationship between PLATFORM_USER_PLATFORM_ROLE Table and
     * PLATFORM_USER Table
     * 
     * @return platformRoles
     */
    @ManyToMany(targetEntity = PlatformRole.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "PLATFORM_USER_PLATFORM_ROLE", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID"))
    public Set<PlatformRole> getPlatformRoles() {
        return platformRoles;
    }

    /**
     * Set platformRoles
     * 
     * @param platformRoles
     */
    public void setPlatformRoles(Set<PlatformRole> platformRoles) {
        this.platformRoles = platformRoles;
    }

    /**
     * Add PlatformRole
     * 
     * @param role
     */
    public void addPlatformRole(PlatformRole role) {
        getPlatformRoles().add(role);
    }

    /**
     * One to Onre relationship between PLATFORM_USER Table and USER_ACCOUNT Table
     * 
     * @return bankingUser
     */
    @OneToOne
    @JoinColumn(name = "BANKING_USER_ID", nullable = true)
    public User getBankingUser() {
        return bankingUser;
    }

    /**
     * Set BannkingUser
     * 
     * @param bankingUser
     */
    public void setBankingUser(User bankingUser) {
        this.bankingUser = bankingUser;
    }

    /**
     * @return userName
     */
    @Override
    public String getName() {
        return getUsername();
    }

}