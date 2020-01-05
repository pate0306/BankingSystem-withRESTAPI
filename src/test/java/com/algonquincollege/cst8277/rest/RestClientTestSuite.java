/**************************************************************G*********o****o****g**o****og**joob*********************
 * File: RestClientTestSuite.java
 * Course materials (19F) CST 8277
 * @author Mike Norman
 *
 * @date 2019 10
 */

/*****************************************************************************************************************************************
 * File: RestClientTestSuite.java
 * Course (19F) CST 8277
 * Updated by : Parth Patel
 *
 * Created Date: 2019-12-06
 */
package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.util.MyConstants.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.util.MyConstants.APPLICATION_NAME;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Before;
import org.junit.Test;

public class RestClientTestSuite {

    WebTarget webTarget;

    @Before
    public void setUp() throws Exception {
        Client client = ClientBuilder.newClient();
        UriBuilder uriBuilder = UriBuilder.fromUri("").scheme("http").host("localhost").port(8090)
                .path("rest-banking/api/v1/");
        webTarget = client.target(uriBuilder);
    }

    @Test
    public void testBasicAuth() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();
        WebTarget webTarget = client.register(feature).target(uri).path("user");
        Response response = webTarget.request(APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));

    }

    /**
     * Checking the First User is getting Delete
     */
    @Test
    public void test2() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("user/1");
        Response response = target.request(APPLICATION_JSON).delete();

        assertThat(response.getStatus(), is(200));

    }

    /**
     * Checking First Account is getting Delete
     */
    @Test
    public void test3() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("account/1");
        Response response = target.request(APPLICATION_JSON).delete();

        assertThat(response.getStatus(), is(200));

    }

    /**
     * Checking getting all the user Recevied
     */
    @Test
    public void test4() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("user");
        Response response = target.request(APPLICATION_JSON).get();

        assertThat(response.getStatus(), is(200));

    }

    /**
     * Checking All the Accounts are getting
     */
    @Test
    public void test5() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("account");
        Response response = target.request(APPLICATION_JSON).get();

        assertThat(response.getStatus(), is(200));

    }

    /**
     * Checking All the Portfolios are getting
     */
    @Test
    public void test6() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("portfolio");
        Response response = target.request(APPLICATION_JSON).get();

        assertThat(response.getStatus(), is(200));

    }

    /**
     * Checking the response getting in JSON formate
     */
    @Test
    public void test7() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("portfolio");
        Response response = target.request(APPLICATION_JSON).get();
        assertThat(response.getMediaType().toString(), is((MediaType.APPLICATION_JSON)));
    }

    /**
     * Checking Asset type is JSON
     */

    @Test
    public void test8() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("asset");
        Response response = target.request(APPLICATION_JSON).get();
        assertThat(response.getMediaType().toString(), is((MediaType.APPLICATION_JSON)));
    }

    /**
     * Checking Portfolio is getting deleted
     */

    @Test
    public void test9() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("portfolio/1");
        Response response = target.request(APPLICATION_JSON).delete();

        assertThat(response.getStatus(), is(200));

    }

    /**
     * Wrong user can not Delete the Portfolio
     */
    @Test
    public void test10() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("123", "123");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();

        WebTarget target = client.register(feature).target(uri).path("portfolio/1");
        Response response = target.request(APPLICATION_JSON).delete();

        assertThat(response.getStatus(), is(401));

    }

    /**
     * Wrong User can not access the Portfolio data
     */
    @Test
    public void test11() {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("123", "123");
        Client client = ClientBuilder.newClient();
        URI uri = UriBuilder.fromUri(APPLICATION_NAME + APPLICATION_API_VERSION).scheme("http").host("localhost")
                .port(8090).build();
        WebTarget webTarget = client.register(feature).target(uri).path("Portfolio");
        Response response = webTarget.request(APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(401));
    }

}