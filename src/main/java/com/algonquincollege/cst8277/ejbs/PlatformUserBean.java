/**
 * Name: Parth Patel
 * Student Number: 040-751-954
 * Class Name : AssetBean.java
 * Created Date: 2019-12-05
 */

package com.algonquincollege.cst8277.ejbs;

import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.util.MyConstants.FIND_PLATFORM_USER_BY_NAME_JPQL;
import static com.algonquincollege.cst8277.util.MyConstants.NAME_PARAM;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_SALTSIZE;
import static com.algonquincollege.cst8277.util.MyConstants.PU_NAME;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.algonquincollege.cst8277.models.PlatformRole;
import com.algonquincollege.cst8277.models.PlatformUser;
import com.algonquincollege.cst8277.models.User;

@Stateless
public class PlatformUserBean {

    @PersistenceContext(unitName = PU_NAME)
    protected EntityManager em;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    /**
     * Find all Platform users
     * 
     * @return platformUser - List of platformUser
     */
    public List<PlatformUser> findAllPlatformUsers() {
        TypedQuery<PlatformUser> q = em.createQuery("SELECT u FROM PlatformUser u", PlatformUser.class);
        return q.getResultList();
    }

    /**
     * Find PlatformUser by userId
     * 
     * @param id
     * @return platformUser - Return platform base on user id
     */
    public PlatformUser findPlatformUserById(int id) {

        PlatformUser platformUser = em.find(PlatformUser.class, id);
        return platformUser;
    }

    /**
     * Find PlatfromUser from Name
     * 
     * @param userName
     * @return platformUser - PlatformUser by username
     */
    public PlatformUser findPlatformUserByName(String userName) {
        PlatformUser platformUser = null;
        try {
            TypedQuery<PlatformUser> q = em.createQuery(FIND_PLATFORM_USER_BY_NAME_JPQL, PlatformUser.class);
            q.setParameter(NAME_PARAM, userName);
            platformUser = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return platformUser;
    }

    /**
     * Add Platform User
     * 
     * @param user
     * @param isUser - Checking User is Present or not
     * @return user - Created PlatformUser
     */
    public PlatformUser addPlatformUser(PlatformUser user, boolean isUser) {

        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(user.getPwHash().toCharArray());
        user.setPwHash(pwHash);
        int adminRoleId;
        int userRoleId;
        PlatformRole userRole;
        PlatformRole temp = em.find(PlatformRole.class, 1);
        if (temp.getRoleName() == "ADMIN_ROLE") {
            adminRoleId = 1;
            userRoleId = 2;
        } else {
            adminRoleId = 2;
            userRoleId = 1;
        }
        if (isUser) {
            userRole = em.find(PlatformRole.class, userRoleId);
        } else {
            userRole = em.find(PlatformRole.class, adminRoleId);
        }

        Set<PlatformRole> platformRoles = user.getPlatformRoles();
        if (platformRoles == null) {
            platformRoles = new HashSet<>();
        }
        platformRoles.add(userRole);
        user.setPlatformRoles(platformRoles);
        em.persist(user);
        return user;
    }

    /**
     * Updating the Platform User
     * 
     * @param user
     * @return user - Updated PlatformUser
     */
    public PlatformUser updatePlatformUser(PlatformUser user) {
        em.merge(user);
        return user;
    }

    /**
     * Changing the password of the user
     * 
     * @param user        - User
     * @param newPassword - new password for the user
     */
    public void changePassword(PlatformUser user, String newPassword) {
        Map<String, String> pbAndjProperties = new HashMap<>();
        pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
        pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
        pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
        pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
        pbAndjPasswordHash.initialize(pbAndjProperties);
        String pwHash = pbAndjPasswordHash.generate(newPassword.toCharArray());
        user.setPwHash(pwHash);
        em.merge(user);
    }

    /**
     * Delete the Platoform User
     * 
     * @param user - To be Deleted Platformuser
     * @return user
     */
    public PlatformUser deletePlatformUser(PlatformUser user) {
        if (!em.contains(user)) {
            user = em.merge(user);
        }
        em.remove(user);
        return user;
    }

    /**
     * Update User Account For Platform User
     * 
     * @param platformUser
     * @param userAccountId
     * @return if user is exits than it will return platformUser otherwise null
     */
    public PlatformUser updateUserAccountForPlatformUser(PlatformUser platformUser, int userAccountId) {

        User user = em.find(User.class, userAccountId);

        if (user != null) {
            platformUser.setBankingUser(user);
            em.merge(platformUser);
            return platformUser;
        } else {
            return null;
        }

    }

}
