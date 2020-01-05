/**
 * Name: Parth Patel
 * Student Number: 040-751-954
 * Class Name : UserBean.java
 * Created Date: 2019-12-01
 */

package com.algonquincollege.cst8277.ejbs;

import static com.algonquincollege.cst8277.util.MyConstants.PU_NAME;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import com.algonquincollege.cst8277.models.User;

@Named
@Stateless
public class UserBean {

    @PersistenceContext(unitName = PU_NAME)
    protected EntityManager em;

    /**
     * Create User
     * 
     * @param user
     * @return user
     */
    // @Transactional
    @Transactional(TxType.REQUIRED)
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

    /**
     * Get Updated the user
     * 
     * @param user
     * @return user- Updated user
     */
    @Transactional
    public User updateUser(User user) {
        em.merge(user);
        return user;
    }

    /**
     * 
     * Get user list
     * 
     * @return user
     */
    public List<User> getAllUsers() {
        Query userQuery = em.createQuery("Select u From User u ", User.class);
        return userQuery.getResultList();
    }

    /**
     * Find the user based on id
     * 
     * @param userId
     * @return user - user based on id
     */
    public User findUserById(int userId) {
        return em.find(User.class, userId);
    }

    /**
     * Delete the User baseed on UserId
     * 
     * @param UserId - Pass the UserId
     * @return boolean - if user is deleted, it will return true otherwise false
     */
    public boolean deleteUser(int userId) {

        User userToDelete = findUserById(userId);
        if (userToDelete != null) {

            em.remove(userToDelete);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Find the User by using PlatformUserId
     * 
     * @param id - PlatformUserId
     * @return user
     */
    public User getUserByPlatformUserId(int id) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u " + "WHERE u.id in "
                + "(SELECT pu.bankingUser.id FROM PlatformUser pu where pu.id = :id)", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
}
