package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Logs.class)
public abstract class Logs_ {

	public static volatile SingularAttribute<Logs, String> logpath;
	public static volatile SingularAttribute<Logs, Workflow> workflow;
	public static volatile SingularAttribute<Logs, String> updateTime;
	public static volatile SingularAttribute<Logs, String> insightVersion;
	public static volatile SingularAttribute<Logs, Volume> volume;
	public static volatile SingularAttribute<Logs, Boolean> running;
	public static volatile SingularAttribute<Logs, Long> sequence;
	public static volatile SingularAttribute<Logs, String> subsurfaces;
	public static volatile SingularAttribute<Logs, String> summaryTime;
	public static volatile SingularAttribute<Logs, Long> idLogs;
	public static volatile SingularAttribute<Logs, Header> header;
	public static volatile SingularAttribute<Logs, Boolean> cancelled;
	public static volatile SingularAttribute<Logs, Boolean> completedsuccessfully;
	public static volatile SingularAttribute<Logs, String> timestamp;
	public static volatile SingularAttribute<Logs, Boolean> errored;

}

