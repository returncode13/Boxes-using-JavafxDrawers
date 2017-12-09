package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Log.class)
public abstract class Log_ {

	public static volatile SingularAttribute<Log, String> logpath;
	public static volatile SingularAttribute<Log, Workflow> workflow;
	public static volatile SingularAttribute<Log, String> updateTime;
	public static volatile SingularAttribute<Log, Subsurface> subsurface;
	public static volatile SingularAttribute<Log, Long> version;
	public static volatile SingularAttribute<Log, String> insightVersion;
	public static volatile SingularAttribute<Log, Volume> volume;
	public static volatile SingularAttribute<Log, Boolean> running;
	public static volatile SingularAttribute<Log, Long> sequence;
	public static volatile SingularAttribute<Log, String> summaryTime;
	public static volatile SingularAttribute<Log, Long> idLogs;
	public static volatile SingularAttribute<Log, Header> header;
	public static volatile SingularAttribute<Log, Boolean> cancelled;
	public static volatile SingularAttribute<Log, Boolean> completedsuccessfully;
	public static volatile SingularAttribute<Log, Job> job;
	public static volatile SingularAttribute<Log, String> timestamp;
	public static volatile SingularAttribute<Log, Boolean> errored;

}

