/***********
 * File Name: PlatformUserResource.java
 * Created By :Parth Patel
 * Created Date: 2019-12-04
 * 
 */

package com.algonquincollege.cst8277.rest;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.algonquincollege.cst8277.ejbs.PlatformUserBean;
import com.algonquincollege.cst8277.models.PlatformUser;
import com.algonquincollege.cst8277.util.MyConstants;

@Path("platformUser")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlatformUserResource implements MyConstants {

    @EJB
    PlatformUserBean platformUserBean;

    @Context
    protected ServletContext servletContext;

    @Inject
    protected SecurityContext sc;

    /**
     * 
     * @return response - PlatformUser is not null than it will return platformUsers
     *         list otherwise return default message
     */
    @GET
    @RolesAllowed(ADMIN_ROLE)
    public Response getUsers() {
        Response response = null;

        List<PlatformUser> platformUsers = platformUserBean.findAllPlatformUsers();

        if (platformUsers != null) {
            response = Response.ok(platformUsers).build();
        } else {
            response = Response.ok("You do not have platform user.").build();
        }
        return response;
    }

    /**
     * Find Platform user by platoform id
     * 
     * @param id -PlatformId
     * @return - PlatformUser is not null than it will return user otherwise it will
     *         return default message
     */
    @GET
    @Path("{id}")
    @RolesAllowed(ADMIN_ROLE)
    public Response getUserById(@PathParam("id") int id) {
        Response response = null;

        PlatformUser user = platformUserBean.findPlatformUserById(id);
        if (user != null) {
            response = Response.ok(user).build();
        } else {
            response = Response.ok("Platform user does not exist.").build();
        }
        return response;
    }

    /**
     * Find PlatformUser from user name
     * 
     * @param platformUser - user name
     * @return - if user is not a Platformuser than it will add in platformUser. if
     *         it presents in Platformuser than return default message
     */
    @POST
    @Path("userRole")
    @RolesAllowed(ADMIN_ROLE)
    public Response addUser(PlatformUser platformUser) {
        Response response = null;
        PlatformUser user = platformUserBean.findPlatformUserByName(platformUser.getUsername());
        if (user == null) {
            user = platformUserBean.addPlatformUser(platformUser, true);
            response = Response.ok(user).build();
        } else {
            response = Response.ok("Please select another username.").build();
        }
        return response;
    }

    @POST
    @Path("adminRole")
    @RolesAllowed(ADMIN_ROLE)
    public Response addAdmin(PlatformUser platformUser) {
        Response response = null;
        PlatformUser user = platformUserBean.findPlatformUserByName(platformUser.getUsername());
        if (user == null) {
            user = platformUserBean.addPlatformUser(platformUser, false);
            response = Response.ok(user).build();
        } else {
            response = Response.ok("Please select another username.").build();
        }
        return response;
    }

    /**
     * Updating the Password
     * 
     * @param id          -userId
     * @param newPassword - newPassword
     * @return - response -> if user is not present, it will return default message
     *         "This user does not exist." otherwise it returns message "Changed
     *         password"
     */
    @PUT
    @Path("{id}/password")
    @RolesAllowed(ADMIN_ROLE)
    public Response changePassword(@PathParam("id") int id, String newPassword) {
        Response response = null;
        PlatformUser user = platformUserBean.findPlatformUserById(id);

        if (user != null) {
            try {
                platformUserBean.changePassword(user, newPassword);
                response = Response.ok("Changed password").build();
            } catch (Exception e) {

                response = Response.ok(e.getMessage()).build();
            }
        } else {
            response = Response.ok("This user does not exist.").build();
        }
        return response;
    }

    /**
     * Update User Account
     * 
     * @param id            -PlatformUserId
     * @param userAccountId - UserAccountId
     * @return response - Updated user Account
     */
    @PUT
    @Path("{id}/user")
    @RolesAllowed(ADMIN_ROLE)
    public Response updateUserAccount(@PathParam("id") int id, int userAccountId) {
        Response response = null;
        PlatformUser user = platformUserBean.findPlatformUserById(id);
        if (user != null) {
            try {
                PlatformUser newUser = platformUserBean.updateUserAccountForPlatformUser(user, userAccountId);
                if (newUser != null) {
                    response = Response.ok(newUser).build();
                } else {
                    response = Response.ok("The user account you want to add is not exist").build();
                }

            } catch (Exception e) {
                response = Response.ok(e.getMessage()).build();
            }
        } else {
            response = Response.ok("This platform user does not exist.").build();
        }
        return response;
    }

    /**
     * 
     * @param id -Platform userId
     * @return - if platformUser is exist, it will get deleted otherwise return
     *         default message
     */
    @DELETE
    @Path("{id}")
    @RolesAllowed(ADMIN_ROLE)
    public Response deleteUser(@PathParam("id") int id) {
        Response response = null;
        PlatformUser user = platformUserBean.findPlatformUserById(id);

        if (user != null) {
            response = Response.ok(platformUserBean.deletePlatformUser(user)).build();
        } else {
            response = Response.ok("This user does not exist.").build();
        }
        return response;
    }
}
