package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Doubt.class)
public abstract class Doubt_ {

	public static volatile SingularAttribute<Doubt, Sequence> sequence;
	public static volatile SetAttribute<Doubt, DoubtStatus> doubtStatuses;
	public static volatile SingularAttribute<Doubt, Doubt> doubtCause;
	public static volatile SetAttribute<Doubt, Doubt> inheritedDoubts;
	public static volatile SingularAttribute<Doubt, Dot> dot;
	public static volatile SingularAttribute<Doubt, Job> childJob;
	public static volatile SingularAttribute<Doubt, DoubtType> doubtType;
	public static volatile SingularAttribute<Doubt, Long> id;
	public static volatile SingularAttribute<Doubt, Subsurface> subsurface;
	public static volatile SingularAttribute<Doubt, User> user;

}

