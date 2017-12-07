package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Workflow.class)
public abstract class Workflow_ {

	public static volatile SingularAttribute<Workflow, Volume> volume;
	public static volatile SingularAttribute<Workflow, Long> wfversion;
	public static volatile SingularAttribute<Workflow, String> contents;
	public static volatile SingularAttribute<Workflow, String> md5sum;
	public static volatile SingularAttribute<Workflow, Long> id;
	public static volatile SingularAttribute<Workflow, String> time;
	public static volatile SetAttribute<Workflow, Logs> logs;

}

