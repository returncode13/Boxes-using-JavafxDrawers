package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SetAttribute<User, DoubtStatus> doubtStatuses;
	public static volatile SetAttribute<User, QcTable> qctables;
	public static volatile SingularAttribute<User, Long> user_id;
	public static volatile SingularAttribute<User, String> initials;
	public static volatile SetAttribute<User, UserWorkspace> userWorkspaces;
	public static volatile SingularAttribute<User, String> fullName;
	public static volatile SetAttribute<User, Workspace> workspaces;
	public static volatile SetAttribute<User, Workspace> ownedWorkspaces;

}

