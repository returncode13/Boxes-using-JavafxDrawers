package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Summary.class)
public abstract class Summary_ {

	public static volatile SingularAttribute<Summary, Boolean> timeSummary;
	public static volatile SingularAttribute<Summary, Sequence> sequence;
	public static volatile SingularAttribute<Summary, Long> depth;
	public static volatile SingularAttribute<Summary, Boolean> inheritanceSummary;
	public static volatile SingularAttribute<Summary, Boolean> traceSummary;
	public static volatile SingularAttribute<Summary, Long> id;
	public static volatile SingularAttribute<Summary, Job> job;
	public static volatile SingularAttribute<Summary, Boolean> qcSummary;
	public static volatile SingularAttribute<Summary, Boolean> insightSummary;

}

