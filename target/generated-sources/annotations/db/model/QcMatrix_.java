package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(QcMatrix.class)
public abstract class QcMatrix_ {

	public static volatile SingularAttribute<QcMatrix, QcType> qctype;
	public static volatile SingularAttribute<QcMatrix, Long> id;
	public static volatile SingularAttribute<QcMatrix, Job> job;
	public static volatile SingularAttribute<QcMatrix, Boolean> present;

}

