
/**
 * Name: Parth Patel
 * Student Number: 040-751-954
 * Class Name : AssetBean.java
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
import javax.transaction.Transactional;
import com.algonquincollege.cst8277.models.Asset;

@Named
@Stateless
public class AssetBean {

    @PersistenceContext(unitName = PU_NAME)
    protected EntityManager em;

    /**
     * Create the Asset
     * 
     * @param asset
     * @return Asset - created Asset
     */
    @Transactional
    public Asset createAsset(Asset asset) {
        em.persist(asset);
        return asset;
    }

    /**
     * Update the Asset
     * 
     * @param asset
     * @return Asset - Updated Asset
     */
    @Transactional
    public Asset updateAsset(Asset asset) {
        em.merge(asset);
        return asset;

    }

    /**
     * Get list of Asset
     * 
     * @return AssetList
     */
    public List<Asset> getAllAssets() {
        Query AssetQuery = em.createQuery("Select a From Asset a ", Asset.class);
        return AssetQuery.getResultList();
    }

    /**
     * Find the Asset base on the id
     * 
     * @param assetId
     * @return Asset - Asset based on id
     */
    public Asset findAssets(int assetId) {
        return em.find(Asset.class, assetId);
    }

    /**
     * Delete the Asset on base on id
     * 
     * @param assetId
     * @return Boolean - if Asset is deleted, will return True otherwise false
     */
    public Boolean deleteAsset(int assetId) {

        Asset assetBaseToDelete = findAssets(assetId);
        if (assetBaseToDelete != null) {

            em.remove(assetBaseToDelete);
            return true;
        } else {
            return false;

        }
    }
}
