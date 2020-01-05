package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-01-04T11:08:22.751-0500")
@StaticMetamodel(User.class)
public class User_ extends ModelBase_ {
	public static volatile SingularAttribute<User, LocalDateTime> createdDate;
	public static volatile SingularAttribute<User, LocalDateTime> updatedDate;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> name;
	public static volatile ListAttribute<User, AccountBase> accounts;
}
