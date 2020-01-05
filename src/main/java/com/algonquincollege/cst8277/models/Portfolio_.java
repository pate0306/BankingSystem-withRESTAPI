package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-01-04T10:13:04.474-0500")
@StaticMetamodel(Portfolio.class)
public class Portfolio_ extends ModelBase_ {
	public static volatile SingularAttribute<Portfolio, LocalDateTime> createdDate;
	public static volatile SingularAttribute<Portfolio, LocalDateTime> updatedDate;
	public static volatile SingularAttribute<Portfolio, Integer> id;
	public static volatile ListAttribute<Portfolio, Asset> assets;
	public static volatile SingularAttribute<Portfolio, Double> balance;
}
