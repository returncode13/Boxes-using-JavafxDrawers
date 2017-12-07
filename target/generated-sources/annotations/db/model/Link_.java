package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Link.class)
public abstract class Link_ {

	public static volatile SetAttribute<Link, Doubt> doubts;
	public static volatile SingularAttribute<Link, Job> parent;
	public static volatile SingularAttribute<Link, Dot> dot;
	public static volatile SingularAttribute<Link, Long> id;
	public static volatile SingularAttribute<Link, Job> child;

}

