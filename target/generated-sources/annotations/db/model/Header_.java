package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Header.class)
public abstract class Header_ {

	public static volatile SingularAttribute<Header, Long> xlineMax;
	public static volatile SingularAttribute<Header, Subsurface> subsurfaceFK;
	public static volatile SingularAttribute<Header, Long> xlineInc;
	public static volatile SingularAttribute<Header, Long> dugShotMax;
	public static volatile SingularAttribute<Header, String> insightVersion;
	public static volatile SingularAttribute<Header, Long> cmpMin;
	public static volatile SingularAttribute<Header, String> textfilepath;
	public static volatile SingularAttribute<Header, Long> dugShotInc;
	public static volatile SingularAttribute<Header, Long> traceCount;
	public static volatile SingularAttribute<Header, Long> inlineMax;
	public static volatile SingularAttribute<Header, Long> inlineInc;
	public static volatile SingularAttribute<Header, Boolean> modified;
	public static volatile SingularAttribute<Header, Long> id;
	public static volatile SingularAttribute<Header, Long> cmpInc;
	public static volatile SetAttribute<Header, Logs> logs;
	public static volatile SingularAttribute<Header, Long> dugShotMin;
	public static volatile SingularAttribute<Header, Long> offsetMin;
	public static volatile SingularAttribute<Header, Long> cmpMax;
	public static volatile SingularAttribute<Header, Long> xlineMin;
	public static volatile SingularAttribute<Header, Long> inlineMin;
	public static volatile SingularAttribute<Header, Long> dugChannelMax;
	public static volatile SingularAttribute<Header, Long> offsetInc;
	public static volatile SingularAttribute<Header, Long> numberOfRuns;
	public static volatile SingularAttribute<Header, String> updateTime;
	public static volatile SingularAttribute<Header, Long> dugChannelInc;
	public static volatile SingularAttribute<Header, Volume> volume;
	public static volatile SingularAttribute<Header, String> timeStamp;
	public static volatile SingularAttribute<Header, Long> offsetMax;
	public static volatile SingularAttribute<Header, Boolean> deleted;
	public static volatile SingularAttribute<Header, String> summaryTime;
	public static volatile SingularAttribute<Header, Long> dugChannelMin;
	public static volatile SingularAttribute<Header, Long> workflowVersion;
	public static volatile SingularAttribute<Header, Job> job;

}

