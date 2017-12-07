package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(QcTable.class)
public abstract class QcTable_ {

	public static volatile SingularAttribute<QcTable, Boolean> result;
	public static volatile SingularAttribute<QcTable, Subsurface> subsurfaceFK;
	public static volatile SingularAttribute<QcTable, String> summaryTime;
	public static volatile SingularAttribute<QcTable, QcMatrix> qcmatrix;
	public static volatile SingularAttribute<QcTable, String> comment;
	public static volatile SingularAttribute<QcTable, String> updateTime;
	public static volatile SingularAttribute<QcTable, Long> id;
	public static volatile SingularAttribute<QcTable, String> time;
	public static volatile SingularAttribute<QcTable, User> user;

}

