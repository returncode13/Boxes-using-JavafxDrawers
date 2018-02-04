package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserWorkspace.class)
public abstract class UserWorkspace_ {

	public static volatile SingularAttribute<UserWorkspace, Workspace> workspace;
	public static volatile SingularAttribute<UserWorkspace, UserWorkspaceId> pk;
	public static volatile SingularAttribute<UserWorkspace, User> user;

}

