package com.algonquincollege.cst8277.rest;

import static com.algonquincollege.cst8277.util.MyConstants.*;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.glassfish.jersey.jackson.JacksonFeature;

@ApplicationPath(APPLICATION_API_VERSION)
@OpenAPIDefinition(info = @Info(title = APPLICATION_OPENAPI_TITLE, version = APPLICATION_OPENAPI_VERSION, description = APPLICATION_OPENAPI_DESCRIPTION), servers = {
        @Server(url = APPLICATION_NAME) }, security = {
                @SecurityRequirement(name = APPLICATION_OPENAPI_SECURITY_SCHEME) })
@SecurityScheme(securitySchemeName = APPLICATION_OPENAPI_SECURITY_SCHEME, type = SecuritySchemeType.HTTP, scheme = APPLICATION_OPENAPI_SECURITY_HTTP_AUTH_SCHEMA)
@DeclareRoles({ USER_ROLE, ADMIN_ROLE }) // or in web.xml, or ServletContextListener
public class RestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(UserResource.class);
        classes.add(AccountResource.class);
        classes.add(PortfolioResource.class);
        classes.add(AssetResource.class);
        classes.add(JacksonFeature.class);
        return classes;
    }

}