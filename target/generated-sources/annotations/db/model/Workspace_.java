package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Workspace.class)
public abstract class Workspace_ {

	public static volatile SetAttribute<Workspace, Job> jobs;
	public static volatile SingularAttribute<Workspace, String> name;
	public static volatile SingularAttribute<Workspace, Long> id;
	public static volatile SingularAttribute<Workspace, User> user;

}

