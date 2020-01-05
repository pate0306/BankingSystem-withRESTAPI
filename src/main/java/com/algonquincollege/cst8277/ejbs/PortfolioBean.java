/**
 * Name: Parth Patel
 * Student Number: 040-751-954
 * Class Name : PortfolioBean.java
 * Created at: 2019-12-01
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
import com.algonquincollege.cst8277.models.Portfolio;

@Named
@Stateless
public class PortfolioBean {

    @PersistenceContext(unitName = PU_NAME)
    protected EntityManager em;

    /**
     * Create Portfolio
     * 
     * @param portfolio - Pass Portfolio object
     * @return - Created Portfolio
     */
    @Transactional
    public Portfolio createPortfolio(Portfolio portfolio) {
        em.persist(portfolio);
        return portfolio;
    }

    /**
     * Update the portfolio based on the Portfolio object
     * 
     * @param portfolio
     * @return - Updated Portfolio
     */
    @Transactional
    public Portfolio upatePortfolio(Portfolio portfolio) {
        em.merge(portfolio);
        return portfolio;
    }

    /**
     * Find the Portfolio based on PortfolioId
     * 
     * @param portfolioId - Pass the portfolio id
     * @return - Portfolio by id
     */
    public Portfolio findPortfolioById(int portfolioId) {
        return em.find(Portfolio.class, portfolioId);
    }

    /**
     * Find all Portfolios
     * 
     * @return List of portfolios
     */
    public List<Portfolio> findAllPortfolio() {
        Query query = em.createQuery("Select p From Portfolio p", Portfolio.class);
        return query.getResultList();
    }

    /**
     * Delete the Portfolio baseed on portfolioId
     * 
     * @param portfolioId - Pass the portfolioId
     * @return boolean
     */
    public boolean deletePortfolio(int portfolioId) {

        Portfolio portfolioToDelete = findPortfolioById(portfolioId);
        if (portfolioToDelete != null) {

            em.remove(portfolioToDelete);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find the list of the Portfoilo by userId
     * 
     * @param id - UserId
     * @return PortfolioList - Find portfolioId by using UserId
     */
    public List<Portfolio> findPortfoliosByUserId(int id) {

        TypedQuery<Portfolio> query = em.createQuery(
                "select p from Portfolio p where p.id IN (select account.portfolio.id from AccountBase account join account.users user where user.id= :id)",
                Portfolio.class);
        query.setParameter("id", id);

        return query.getResultList();
    }
}
