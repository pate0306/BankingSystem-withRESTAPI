/***********
 * File Name: AssetResource.java
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
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import com.algonquincollege.cst8277.ejbs.AssetBean;
import com.algonquincollege.cst8277.ejbs.PortfolioBean;
import com.algonquincollege.cst8277.models.Asset;
import com.algonquincollege.cst8277.models.Portfolio;
import com.algonquincollege.cst8277.security.CustomIdentityStoreJPAHelper;

@Path("asset")
public class AssetResource {

    @EJB
    protected AssetBean assetBean;

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Inject
    protected SecurityContext sc;

    @EJB
    protected PortfolioBean bean;

    /**
     * Getting all Assets
     * 
     * @return response - All the Assets
     */
    @GET
    @RolesAllowed({ ADMIN_ROLE })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssets() {
        Response response = null;
        List<Asset> assetsList = assetBean.getAllAssets();
        response = Response.ok(assetsList).build();
        return response;

    }

    /**
     * Create Asset base on Portfolio id
     * 
     * @param id    -Portfolio id
     * @param asset - Asset info
     * @return response - if portofolio does not extit, it will return default
     *         message otherwise return Asset
     */
    @RolesAllowed({ ADMIN_ROLE })
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAsset(@PathParam("id") int id, @RequestBody Asset asset) {
        Response response = null;
        Portfolio portfolio = bean.findPortfolioById(id);
        if (portfolio != null) {
            Asset asset2 = assetBean.createAsset(asset);
            portfolio.addAsset(asset2);
            response = Response.ok(portfolio).build();
        } else {
            response = Response.ok("This portfolio doesn't exist").build();
        }
        return response;
    }

    /**
     * Update Asset base on Asset id
     * 
     * @param id    - Asset id
     * @param asset - Asset information
     * @return response - Update asset. If input is not Asset type, it will return
     *         default message
     */
    @RolesAllowed({ ADMIN_ROLE })
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBankAccounts(@PathParam("id") int id, @RequestBody Asset asset) {
        Response response = null;

        Asset asset1 = null;

        if (asset instanceof Asset) {
            Asset assetToBeUpdated = (Asset) assetBean.findAssets(id);
            assetToBeUpdated.setBalance(asset.getBalance());
            assetToBeUpdated.setName(asset.getName());
            assetToBeUpdated.setOwner(asset.getOwner());
            assetToBeUpdated.setPrice(asset.getPrice());
            assetToBeUpdated.setUnits(asset.getUnits());
            asset1 = assetBean.updateAsset(assetToBeUpdated);
            response = Response.ok(asset1).build();
        } else {
            response = Response.ok("It is not Asset type instance").build();
        }

        return response;

    }

    /**
     * Delete Asset base on Asset id
     * 
     * @param id -Asset id
     * @return response- If Asset is deleted, it return True otherwise false
     */
    @RolesAllowed({ ADMIN_ROLE })
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAsset(@PathParam("id") int id) {
        Response response = null;
        boolean deleteAsset = assetBean.deleteAsset(id);
        response = Response.ok(deleteAsset).build();
        return response;
    }

}
