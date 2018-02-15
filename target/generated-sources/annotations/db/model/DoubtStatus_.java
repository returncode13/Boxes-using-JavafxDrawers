package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DoubtStatus.class)
public abstract class DoubtStatus_ {

	public static volatile SingularAttribute<DoubtStatus, String> timeStamp;
	public static volatile SingularAttribute<DoubtStatus, String> reason;
	public static volatile SingularAttribute<DoubtStatus, String> comments;
	public static volatile SingularAttribute<DoubtStatus, Doubt> doubt;
	public static volatile SingularAttribute<DoubtStatus, Long> id;
	public static volatile SingularAttribute<DoubtStatus, User> user;
	public static volatile SingularAttribute<DoubtStatus, String> status;

}

