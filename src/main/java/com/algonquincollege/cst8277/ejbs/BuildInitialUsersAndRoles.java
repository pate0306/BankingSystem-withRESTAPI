/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: BuildDefaultUsersAndRoles.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */
package com.algonquincollege.cst8277.ejbs;

import static com.algonquincollege.cst8277.util.MyConstants.ADMIN_ROLE;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_ADMIN_USER;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_ADMIN_USER_PASSWORD_PROPNAME;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_ADMIN_USER_PROPNAME;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_KEY_SIZE;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.util.MyConstants.DEFAULT_SALT_SIZE;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_ALGORITHM;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_ITERATIONS;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_KEYSIZE;
import static com.algonquincollege.cst8277.util.MyConstants.PROPERTY_SALTSIZE;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.algonquincollege.cst8277.models.PlatformRole;
import com.algonquincollege.cst8277.models.PlatformUser;
import com.algonquincollege.cst8277.security.CustomIdentityStoreJPAHelper;

/**
 * This Stateless Session bean is 'special' because it is also a Singleton and
 * it runs at startup.
 * 
 * How do we 'bootstrap' the security system? This EJB checks to see if the
 * default ADMIN user has already been created. If not, it then builds the ADMIN
 * role, the USER role and the default ADMIN user (with role of ADMIN) and
 * stores them in the database.
 * 
 * The name of the default ADMIN user is stored in the
 * META-INF/microprofile-config.properties file, along with its initial password
 * 
 */
@Startup
@Singleton
public class BuildInitialUsersAndRoles {

    @Inject
    protected CustomIdentityStoreJPAHelper jpaHelper;

    @Inject
    @ConfigProperty(name = DEFAULT_ADMIN_USER_PROPNAME, defaultValue = DEFAULT_ADMIN_USER)
    private String defaultAdminUsername;

    @Inject
    @ConfigProperty(name = DEFAULT_ADMIN_USER_PASSWORD_PROPNAME)
    private String defaultAdminUserPassword;

    @Inject
    protected Pbkdf2PasswordHash pbAndjPasswordHash;

    @PostConstruct
    public void init() {
        // build default admin user (if needed)
        PlatformUser defaultAdminUser = jpaHelper.findUserByName(defaultAdminUsername);
        if (defaultAdminUser == null) {
            defaultAdminUser = new PlatformUser();
            defaultAdminUser.setUsername(defaultAdminUsername);
            Map<String, String> pbAndjProperties = new HashMap<>();
            pbAndjProperties.put(PROPERTY_ALGORITHM, DEFAULT_PROPERTY_ALGORITHM);
            pbAndjProperties.put(PROPERTY_ITERATIONS, DEFAULT_PROPERTY_ITERATIONS);
            pbAndjProperties.put(PROPERTY_SALTSIZE, DEFAULT_SALT_SIZE);
            pbAndjProperties.put(PROPERTY_KEYSIZE, DEFAULT_KEY_SIZE);
            pbAndjPasswordHash.initialize(pbAndjProperties);
            System.out.println("=======================================");
            System.out.println(defaultAdminUserPassword.toCharArray());
            String pwHash = pbAndjPasswordHash.generate(defaultAdminUserPassword.toCharArray());
            defaultAdminUser.setPwHash(pwHash);

            PlatformRole theAdminRole = new PlatformRole();
            theAdminRole.setRoleName(ADMIN_ROLE);
            Set<PlatformRole> platformRoles = defaultAdminUser.getPlatformRoles();
            if (platformRoles == null) {
                platformRoles = new HashSet<>();
            }
            platformRoles.add(theAdminRole);
            defaultAdminUser.setPlatformRoles(platformRoles);
            jpaHelper.savePlatformUser(defaultAdminUser);

        }

    }
}
