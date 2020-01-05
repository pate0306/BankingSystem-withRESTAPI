/***********
 * File Name: UserResource.java
 * Created By :Parth Patel
 * Created Date: 2019-12-03
 * 
 */

package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.util.MyConstants.ADMIN_ROLE;

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
import org.glassfish.soteria.WrappingCallerPrincipal;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import com.algonquincollege.cst8277.ejbs.UserBean;
import com.algonquincollege.cst8277.models.PlatformUser;
import com.algonquincollege.cst8277.models.User;
import com.algonquincollege.cst8277.security.CustomIdentityStoreJPAHelper;

@Path("user")
public class UserResource {

    @EJB
    protected UserBean userBean;

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Inject
    protected SecurityContext sc;

    /**
     * Get all users for ADMIN_ROLE or a user which is logging in
     * 
     * @return response- if no user available than default message return otherwise
     *         user information display
     */
    @GET
    @RolesAllowed({ ADMIN_ROLE })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getuser() {

        Response response = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            List<User> userList = userBean.getAllUsers();

            for (User user : userList) {
                user.setAccounts(null);
            }

            response = Response.ok(userList).build();
        } else {
            WrappingCallerPrincipal callerPrincipal = (WrappingCallerPrincipal) sc.getCallerPrincipal();
            PlatformUser platformUser = (PlatformUser) callerPrincipal.getWrapped();

            try {
                User user = userBean.findUserById(platformUser.getId());
                user.setAccounts(null);
                response = Response.ok(user).build();
            } catch (Exception exception) {
                exception.printStackTrace();
                response = Response.ok("Sorry, no user available").build();
            }
        }
        return response;

    }

    /**
     * Find the user by id
     * 
     * @param id -userId
     * @return if user do not available than it will display default message
     *         otherwise dipslay the user information
     */
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersById(@PathParam("id") int id) {
        Response response = null;
        User user = userBean.findUserById(id);
        if (user != null) {
            response = Response.ok(user).build();
        } else {
            response = Response.ok("User does not exist.").build();
        }

        return response;
    }

    /**
     * Create the User
     * 
     * @param user
     * @return response -created user
     */
    @RolesAllowed({ ADMIN_ROLE })
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@RequestBody User user) {

        Response response = null;

        if (sc.isCallerInRole(ADMIN_ROLE)) {
            User userCreated = userBean.createUser(user);
            userCreated.setAccounts(null);
            response = Response.ok(userCreated).build();
        }

        else {

            response = Response.ok("User can not Allow to Create User By him self").build();
        }

        return response;
    }

    /**
     * User information is updated base on id
     * 
     * @param id
     * @param userDetails
     * @return - Particular user Information get Updated
     */
    @RolesAllowed({ ADMIN_ROLE })
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, @RequestBody User userDetails) {

        Response response = null;

        User user1 = null;
        if (userDetails instanceof User) {
            User userToBeUpdatd = (User) userBean.findUserById(id);
            if (userToBeUpdatd != null) {
                userToBeUpdatd.setName(userDetails.getName());
                userToBeUpdatd.setAccounts(userDetails.getAccounts());
                user1 = userBean.updateUser(userToBeUpdatd);
                response = Response.ok(user1).build();
            } else {
                response = Response.ok("The User doesn't exist.").build();
            }

        }

        return response;

    }

    /**
     * User get Deleted base on userId
     * 
     * @param id -userId
     * @return - user get deleted
     */
    @RolesAllowed({ ADMIN_ROLE })
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {

        Response response = null;
        User user = userBean.findUserById(id);
        if (sc.isCallerInRole(ADMIN_ROLE)) {
            if (user != null) {
                boolean deleteUser = userBean.deleteUser(id);

                response = Response.ok(deleteUser).build();
            } else {
                response = Response.ok("This user doesn't exist.").build();
            }

        }

        return response;

    }

}
