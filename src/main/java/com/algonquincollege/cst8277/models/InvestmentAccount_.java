package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-12-18T14:50:52.647-0500")
@StaticMetamodel(InvestmentAccount.class)
public class InvestmentAccount_ extends AccountBase_ {
	public static volatile SingularAttribute<InvestmentAccount, LocalDateTime> createdDate;
	public static volatile SingularAttribute<InvestmentAccount, LocalDateTime> updatedDate;
	public static volatile SingularAttribute<InvestmentAccount, Portfolio> portfolio;
}
