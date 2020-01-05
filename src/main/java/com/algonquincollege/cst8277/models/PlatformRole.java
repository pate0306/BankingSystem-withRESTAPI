/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: PlatformRole.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */

/*********
 * File: PlatformRole.java
 * Updated By : Parth Patel
 * Updated Date: 2019-12-03
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Role class used for (JSR-375) Java EE Security authorization/authentication
 */

@Entity
@Table(name = "PLATFORM_ROLE")
public class PlatformRole extends ModelBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    // Declared the variable roleName type String
    protected String roleName;
    // Declared the platformUsers List type PlatformUser
    protected List<PlatformUser> platformUsers;

    public PlatformRole() {
        super();
    }

    /**
     * Get the Role_id
     * 
     * @return id
     */
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    public int getId() {
        return id;
    }

    /**
     * Set the RoleId
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the RoleName
     * 
     * @return roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set the RoleName
     * 
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Many to Many relationship between PLATFORM_USER_PLATFORM_ROLE Table and
     * PLATFORM_ROLE Table
     * 
     * @return platformUsers
     */
    @ManyToMany(mappedBy = "platformRoles")
    public List<PlatformUser> getPlatformUsers() {
        return platformUsers;
    }

    /**
     * Set platformUsers list
     * 
     * @param platformUsers
     */
    public void setPlatformUsers(List<PlatformUser> platformUsers) {
        this.platformUsers = platformUsers;
    }

    /**
     * Add PlatformUser
     * 
     * @param user
     */
    public void addPlatformUser(PlatformUser user) {
        getPlatformUsers().add(user);
    }

}