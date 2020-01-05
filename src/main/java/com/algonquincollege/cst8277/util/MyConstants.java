package com.algonquincollege.cst8277.util;

/**
 * <p>
 * This class holds various constants used by this App's artifacts (Resources, Servlets, etc.)
 * <p>
 * The key idea here is that often an annotation contains String-based parameters that <b><u>must be an exact match</u></b> <br/>
 * to a string used elsewhere. Use of this type of 'Contants' Interface class prevents errors such as:
<blockquote><pre>
{@literal @}GET
{@literal @}Path("{<b><u>emailID</u></b>}/project")  //accidently capitalized <b><u>ID</u></b>, instead of camel-case <b><u>Id</u></b>
public List<Project> getProjects({@literal @}PathParam("<b><u>emailId</u></b>") String emailId) ...  // path parameter does not match Annotation
</pre></blockquote>
 *
 * @author mwnorman
 */
public interface MyConstants {
    
    // constants on Interfaces are 'public static final' by default,
    // but I leave 'em in case I move something to a Class
    
    //OpenAPI constants
    public static final String APPLICATION_OPENAPI_TITLE = "REST Banking Demo with Security";
    public static final String APPLICATION_OPENAPI_VERSION = "0.0.1";
    public static final String APPLICATION_OPENAPI_DESCRIPTION = "API for REST Banking";
    public static final String APPLICATION_OPENAPI_SECURITY_SCHEME =  "basicAuth";
    public static final String APPLICATION_OPENAPI_SECURITY_HTTP_AUTH_SCHEMA = "basic";

    //Security constants
    public static final String USER_ROLE = "USER_ROLE";
    public static final String ADMIN_ROLE = "ADMIN_ROLE";
    
    // the nickname of this Hash algorithm is 'PBandJ' (Peanut-Butter-And-Jam, like the sandwich!)
    // I would like to use the constants from org.glassfish.soteria.identitystores.hash.Pbkdf2PasswordHashImpl
    // but they are not visible, so type in them all over again :-( Hope there are no typos!
    public static final String PROPERTY_ALGORITHM  = "Pbkdf2PasswordHash.Algorithm";
    public static final String DEFAULT_PROPERTY_ALGORITHM  = "PBKDF2WithHmacSHA256";
    public static final String PROPERTY_ITERATIONS = "Pbkdf2PasswordHash.Iterations";
    public static final String DEFAULT_PROPERTY_ITERATIONS = "2048";
    public static final String PROPERTY_SALTSIZE   = "Pbkdf2PasswordHash.SaltSizeBytes";
    public static final String DEFAULT_SALT_SIZE   = "32";
    public static final String PROPERTY_KEYSIZE    = "Pbkdf2PasswordHash.KeySizeBytes";
    public static final String DEFAULT_KEY_SIZE    = "32";
    public static final String DEFAULT_ADMIN_USER_PROPNAME = "default-admin-user";
    public static final String DEFAULT_ADMIN_USER = "admin";
    public static final String DEFAULT_ADMIN_USER_PASSWORD_PROPNAME = "default-admin-user-password";

    //JPA constants
    public static final String PU_NAME = "assignment4-PU";
    public static final String PLATFORM_USER_TABLE_NAME = "PLATFORM_USER";
    public static final String PLATFORM_USER_JOIN_TABLE_NAME = "PLATFORM_USER_ROLE";
    public static final String NAME_PARAM = "nameParam";
    public static final String FIND_PLATFORM_USER_BY_NAME_JPQL =
        "SELECT u FROM PlatformUser u WHERE u.username = :" + NAME_PARAM;
    
    //REST constants
    public static final String APPLICATION_API_VERSION = "/api/v1";
    public static final String APPLICATION_NAME = "/rest-banking";
    

}