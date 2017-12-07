package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Subsurface.class)
public abstract class Subsurface_ {

	public static volatile SetAttribute<Subsurface, Doubt> doubts;
	public static volatile SingularAttribute<Subsurface, Sequence> sequence;
	public static volatile SetAttribute<Subsurface, Header> headers;
	public static volatile SetAttribute<Subsurface, QcTable> qctables;
	public static volatile SingularAttribute<Subsurface, Long> id;
	public static volatile SetAttribute<Subsurface, Acquisition> acquisition;
	public static volatile SingularAttribute<Subsurface, String> subsurface;

}

