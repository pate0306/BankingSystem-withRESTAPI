/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: BankingBean.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
/**
 * Name: Parth Patel
 * Student Number: 040-751-954
 * Class Name : AssetBean.java
 * Updated Date: 2019-12-03
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
import com.algonquincollege.cst8277.models.AccountBase;
import com.algonquincollege.cst8277.models.InvestmentAccount;

@Named
@Stateless
public class BankingBean {

    @PersistenceContext(unitName = PU_NAME)
    protected EntityManager em;

    /**
     * Create the Account
     * 
     * @param accountBase
     * @return AccountBase
     */
    @Transactional(TxType.REQUIRED)
    public AccountBase createAccountBase(AccountBase accountBase) {
        em.persist(accountBase);
        return accountBase;
    }

    /**
     * Update the Acount info
     * 
     * @param accountBase
     * @return AccountBase
     */
    @Transactional
    public AccountBase updateAccountBase(AccountBase accountBase) {
        em.merge(accountBase);
        return accountBase;
    }

    /**
     * Get he list of Account
     * 
     * @return AccountBase List
     */
    public List<AccountBase> getAllBankAccounts() {
        Query accountQuery = em.createQuery("Select b From AccountBase b ", AccountBase.class);
        return accountQuery.getResultList();
    }

    /**
     * Find the Account based on Account id
     * 
     * @param accountId
     * @return AccountBase
     */
    public AccountBase findAccountBaseById(int accountId) {
        return em.find(AccountBase.class, accountId);
    }

    /**
     * Delete the Account based on id
     * 
     * @param accountId
     * @return boolean - if Account is deleted, will return True otherwise False
     */
    public boolean deleteBankAccount(int accountId) {

        AccountBase accountBaseToDelete = findAccountBaseById(accountId);
        if (accountBaseToDelete != null) {

            em.remove(accountBaseToDelete);
            return true;
        } else {
            return false;

        }
    }

    /**
     * Find the Account by AccountID and UserID
     * 
     * @param accountId -AccountId
     * @param userId    - User Id
     * @return account - find the account base on accountId and UserId
     */
    public InvestmentAccount findAccountByIdAndByUserId(int accountId, int userId) {

        TypedQuery<InvestmentAccount> query = em.createQuery(
                "select account from AccountBase account where account.id =:accountId join account.users user where user.id= :userId",
                InvestmentAccount.class);
        query.setParameter("accountId", accountId);
        query.setParameter("userId", userId);

        return query.getSingleResult();
    }

    /**
     * Find Accounts list by using UserId
     * 
     * @param id -UserId
     * @return account - Find the Accounts by UserId
     */
    public List<AccountBase> findAccountsByUserId(int id) {

        TypedQuery<AccountBase> q = em.createQuery(
                "select account from AccountBase account join account.users user where user.id= :id",
                AccountBase.class);
        q.setParameter("id", id);

        return q.getResultList();
    }

}