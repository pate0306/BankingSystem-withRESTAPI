/***********
 * File Name: AccountResource.java
 * Created By :Parth Patel
 * Created Date: 2019-12-03
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
import com.algonquincollege.cst8277.ejbs.BankingBean;
import com.algonquincollege.cst8277.ejbs.UserBean;
import com.algonquincollege.cst8277.models.AccountBase;
import com.algonquincollege.cst8277.models.ChequingAccount;
import com.algonquincollege.cst8277.models.InvestmentAccount;
import com.algonquincollege.cst8277.models.PlatformUser;
import com.algonquincollege.cst8277.models.SavingsAccount;
import com.algonquincollege.cst8277.models.User;

@Path("account")
public class AccountResource {

    @EJB
    protected BankingBean bean;

    @EJB
    protected UserBean userBean;

    @Inject
    protected SecurityContext sc;

    /**
     * Getting Bank Accounts
     * 
     * @return response - List of Accounts of all users if user as Admin privileges
     *         and if it has User privileges than it will return particular user
     *         Accounts
     */
    @GET
    @RolesAllowed({ ADMIN_ROLE, USER_ROLE })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankAccounts() {
        Response response = null;
        if (!sc.isCallerInRole(ADMIN_ROLE)) {
            WrappingCallerPrincipal callerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            PlatformUser platformUser = (PlatformUser) callerPrincipal.getWrapped();
            User user = userBean.getUserByPlatformUserId(platformUser.getId());
            List<AccountBase> accounts = bean.findAccountsByUserId(user.getId());
            response = Response.ok(accounts).build();
        } else {
            List<AccountBase> accounts = bean.getAllBankAccounts();
            response = Response.ok(accounts).build();
        }
        return response;

    }

    /**
     * Finding Acoount base on id
     * 
     * @param id - Account id
     * @return response - Return Account base on id
     */
    @GET
    @Path("{id}")
    @RolesAllowed({ ADMIN_ROLE })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankAccount(@PathParam("id") int id) {
        Response response = null;
        try {
            AccountBase account = bean.findAccountBaseById(id);
            if (account != null) {
                response = Response.ok(account).build();
            } else {
                response = Response.ok("Account does not exit").build();
            }

        } catch (Exception e) {
            response = Response.ok(e.getMessage()).build();
        }

        return response;
    }

    /**
     * Create Account. If created by an User, then add that User to Account also."
     * 
     * @param id      -User id
     * @param account - Account info
     * @return response -New Bank Accounts
     */
    @RolesAllowed({ ADMIN_ROLE, USER_ROLE })
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setBankAccounts(@RequestBody AccountBase account) {
        Response response = null;

        if (!sc.isCallerInRole(ADMIN_ROLE)) {
            try {
                WrappingCallerPrincipal wcp = (WrappingCallerPrincipal) sc.getCallerPrincipal();
                PlatformUser platformUser = (PlatformUser) wcp.getWrapped();
                User user = userBean.getUserByPlatformUserId(platformUser.getId());
                account.addOwner(user);
                AccountBase newAccount = bean.createAccountBase(account);
                user.addAccount(newAccount);
                userBean.updateUser(user);
                response = Response.ok(newAccount).build();
            } catch (Exception exception) {
                response = Response.ok(exception.getMessage()).build();
            }

        } else {

            try {
                response = Response.ok(bean.createAccountBase(account)).build();
            } catch (Exception e) {
                response = Response.ok(e.getMessage()).build();
            }
        }
        return response;
    }

    /**
     * Update the Account Base on Accountid
     * 
     * @param id
     * @param accountBase
     * @return - Updated Account base on AccounId
     */
    @RolesAllowed({ ADMIN_ROLE })
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBankAccounts(@PathParam("id") int id, @RequestBody AccountBase accountBase) {
        Response response = null;

        AccountBase accountBase1 = null;
        if (accountBase instanceof InvestmentAccount) {
            InvestmentAccount tobeUpdated = (InvestmentAccount) bean.findAccountBaseById(id);
            tobeUpdated.setOwners(accountBase.getOwners());
            tobeUpdated.setBalance(accountBase.getBalance());
            tobeUpdated.setPortfolio(((InvestmentAccount) accountBase).getPortfolio());
            accountBase1 = bean.updateAccountBase(tobeUpdated);
        } else if (accountBase instanceof SavingsAccount) {
            SavingsAccount tobeUpdatedSavingAccount = (SavingsAccount) bean.findAccountBaseById(id);
            tobeUpdatedSavingAccount.setOwners(accountBase.getOwners());
            tobeUpdatedSavingAccount.setBalance(accountBase.getBalance());
            tobeUpdatedSavingAccount.setSavingRate(((SavingsAccount) accountBase).getSavingRate());
            accountBase1 = bean.updateAccountBase(tobeUpdatedSavingAccount);
        } else if (accountBase instanceof ChequingAccount) {
            ChequingAccount tobeUpdatedChequingAccount = (ChequingAccount) bean.findAccountBaseById(id);
            tobeUpdatedChequingAccount.setOwners(accountBase.getOwners());
            tobeUpdatedChequingAccount.setBalance(accountBase.getBalance());
            accountBase1 = bean.updateAccountBase(tobeUpdatedChequingAccount);
        }
        response = Response.ok(accountBase1).build();

        return response;

    }

    /**
     * Delete the Acoount Base on id
     * 
     * @param id- Account id
     * @return response - If Account get deleted it will return True otherwise
     *         default message "Account can not Delete by User"
     */
    @RolesAllowed({ ADMIN_ROLE })
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBankAcount(@PathParam("id") int id) {
        Response response = null;
        if (sc.isCallerInRole(ADMIN_ROLE)) {
            boolean deleteAccount = bean.deleteBankAccount(id);
            response = Response.ok(deleteAccount).build();
        } else {
            response = Response.ok("Account can not Delete by User").build();

        }

        return response;
    }

}
