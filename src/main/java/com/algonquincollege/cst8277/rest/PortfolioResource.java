/***********
 * File Name: PortfolioResource.java
 * Created By :Parth Patel
 * Created Date: 2019-12-04
 * 
 */

package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.util.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.util.MyConstants.USER_ROLE;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.glassfish.soteria.WrappingCallerPrincipal;
import com.algonquincollege.cst8277.ejbs.AssetBean;
import com.algonquincollege.cst8277.ejbs.BankingBean;
import com.algonquincollege.cst8277.ejbs.PortfolioBean;
import com.algonquincollege.cst8277.ejbs.UserBean;
import com.algonquincollege.cst8277.models.InvestmentAccount;
import com.algonquincollege.cst8277.models.PlatformUser;
import com.algonquincollege.cst8277.models.Portfolio;
import com.algonquincollege.cst8277.models.User;
import com.algonquincollege.cst8277.security.CustomIdentityStoreJPAHelper;

@Path("portfolio")
public class PortfolioResource {

    @EJB
    protected BankingBean bankingBean;
    @EJB
    protected AssetBean assetBean;

    @EJB
    protected PortfolioBean bean;

    @EJB
    protected UserBean userBean;

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Inject
    protected SecurityContext sc;

    /**
     * Get all Portfolio for ADMIN_ROLE or a user Portfolio which is logging in
     * 
     * @return response- user has Admin privilleges than it will return all
     *         Portfolios otherwise return particular user Portfolio
     */
    @RolesAllowed({ ADMIN_ROLE, USER_ROLE })
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPortfolio() {
        Response response = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            List<Portfolio> portfolioList = bean.findAllPortfolio();
            response = Response.ok(portfolioList).build();
        } else {

            WrappingCallerPrincipal callerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            PlatformUser platformUser = (PlatformUser) callerPrincipal.getWrapped();
            User user = userBean.getUserByPlatformUserId(platformUser.getId());
            Portfolio portfolio = (Portfolio) bean.findPortfoliosByUserId(user.getId());
            response = Response.ok(portfolio).build();

        }

        return response;
    }

    /**
     * Get Portfoilo base on PortfolioId for Admin
     * 
     * @param id - PortfolioId
     * @return response - Return Portfolio base on PortfolioId
     */
    @GET
    @Path("{id}")
    @RolesAllowed({ ADMIN_ROLE })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPortfolioById(@PathParam("id") int id) {
        Portfolio portfolio = bean.findPortfolioById(id);
        return Response.ok(portfolio).build();
    }

    /**
     * Create Portfolio
     * 
     * @param id        -PortfolioId
     * @param portfolio -Portfolio information
     * @return -find the Account and User from their Id and if both are avialble ,
     *         create Portfolio for that particular User and Account
     */
    @RolesAllowed({ ADMIN_ROLE, USER_ROLE })
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPortfolio(@PathParam("id") int id, @RequestBody Portfolio portfolio) {
        Response response = null;
        if (sc.isCallerInRole(ADMIN_ROLE)) {
            Portfolio portfolio2 = bean.createPortfolio(portfolio);
            response = Response.ok(portfolio2).build();
            return response;
        } else {

            WrappingCallerPrincipal wcp = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            PlatformUser platformUser = (PlatformUser) wcp.getWrapped();
            User user = userBean.getUserByPlatformUserId(platformUser.getId());

            InvestmentAccount account = bankingBean.findAccountByIdAndByUserId(id, user.getId());
            if (account != null) {
                Portfolio newPortfolio = bean.createPortfolio(portfolio);
                account.setPortfolio(newPortfolio);
                response = Response.ok(newPortfolio).build();
            } else {
                response = Response.ok("Account doesnt exist").build();
            }
        }

        return response;
    }

    /**
     * Update Portfolio base on PortfolioId
     * 
     * @param id               -PortfolioId
     * @param portfoliodetails
     * @return - Updated Portfolio
     */
    @RolesAllowed({ ADMIN_ROLE, USER_ROLE })
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePortfolio(@PathParam("id") int id, @RequestBody Portfolio portfoliodetails) {

        Portfolio portfolio = bean.findPortfolioById(id);
        portfolio.setBalance(portfoliodetails.getBalance());
        portfolio.setAssets(portfoliodetails.getAssets());
        Portfolio updatePortfolio = bean.upatePortfolio(portfolio);

        return Response.ok(updatePortfolio).build();

    }

    /**
     * Delete the Portfolio
     * 
     * @param id
     * @return if Portfolio was not deleted will return default message "Failed to
     *         Delete Portfolio!" otherwise return default message "Portfolio
     *         deleted successfully!"
     */
    @DELETE
    @Path("{id}")
    @RolesAllowed({ ADMIN_ROLE })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePortfolio(@PathParam("id") int id) {
        boolean result = bean.deletePortfolio(id);
        if (result)
            return Response.ok("Portfolio deleted successfully!").build();
        else
            return Response.ok("Failed to Delete Portfolio!").build();

    }

}
