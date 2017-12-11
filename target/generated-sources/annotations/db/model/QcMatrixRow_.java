package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(QcMatrixRow.class)
public abstract class QcMatrixRow_ {

	public static volatile SingularAttribute<QcMatrixRow, QcType> qctype;
	public static volatile SingularAttribute<QcMatrixRow, Long> id;
	public static volatile SingularAttribute<QcMatrixRow, Job> job;
	public static volatile SingularAttribute<QcMatrixRow, Boolean> present;

}

