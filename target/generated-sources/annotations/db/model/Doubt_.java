package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Doubt.class)
public abstract class Doubt_ {

	public static volatile SingularAttribute<Doubt, Link> link;
	public static volatile SingularAttribute<Doubt, String> errorMessage;
	public static volatile SingularAttribute<Doubt, DoubtType> doubtType;
	public static volatile SingularAttribute<Doubt, Long> id;
	public static volatile SingularAttribute<Doubt, Subsurface> subsurface;
	public static volatile SingularAttribute<Doubt, User> user;
	public static volatile SingularAttribute<Doubt, String> status;

}

