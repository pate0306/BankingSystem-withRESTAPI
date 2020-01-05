/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.util.MyConstants.FIND_PLATFORM_USER_BY_NAME_JPQL;
import static com.algonquincollege.cst8277.util.MyConstants.NAME_PARAM;
import static com.algonquincollege.cst8277.util.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.util.Set;

import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.algonquincollege.cst8277.models.PlatformRole;
import com.algonquincollege.cst8277.models.PlatformUser;

@Singleton
public class CustomIdentityStoreJPAHelper {

    @PersistenceContext(name = PU_NAME)
    protected EntityManager em;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    public PlatformUser findUserByName(String username) {
        PlatformUser platformUser = null;
        try {
            TypedQuery<PlatformUser> q = em.createQuery(FIND_PLATFORM_USER_BY_NAME_JPQL, PlatformUser.class);
            q.setParameter(NAME_PARAM, username);
            platformUser = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return platformUser;
    }

    public Set<PlatformRole> findRolesForUser(String username) {
        Set<PlatformRole> foundRoles = emptySet();
        PlatformUser platformUser = findUserByName(username);
        if (platformUser != null) {
            foundRoles = platformUser.getPlatformRoles();
        }
        return foundRoles;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void savePlatformUser(PlatformUser platformUser) {
        em.persist(platformUser);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void savePlatformRole(PlatformRole platformRole) {
        em.persist(platformRole);
    }

}