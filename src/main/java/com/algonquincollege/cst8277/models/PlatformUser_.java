package com.algonquincollege.cst8277.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-01-04T11:46:37.870-0500")
@StaticMetamodel(PlatformUser.class)
public class PlatformUser_ extends ModelBase_ {
	public static volatile SingularAttribute<PlatformUser, Integer> id;
	public static volatile SetAttribute<PlatformUser, PlatformRole> platformRoles;
	public static volatile SingularAttribute<PlatformUser, User> bankingUser;
	public static volatile SingularAttribute<PlatformUser, String> username;
	public static volatile SingularAttribute<PlatformUser, String> pwHash;
}
