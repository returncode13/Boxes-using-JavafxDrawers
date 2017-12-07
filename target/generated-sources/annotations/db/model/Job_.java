package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Job.class)
public abstract class Job_ {

	public static volatile SetAttribute<Job, Header> headers;
	public static volatile SingularAttribute<Job, Workspace> workspace;
	public static volatile SetAttribute<Job, Ancestor> currentJobInAncestor;
	public static volatile SetAttribute<Job, Link> linksWithJobAsChild;
	public static volatile SingularAttribute<Job, NodeType> nodetype;
	public static volatile SetAttribute<Job, QcMatrix> qcmatrices;
	public static volatile SingularAttribute<Job, String> nameJobStep;
	public static volatile SetAttribute<Job, JobVolumeMap> jobVolumeMap;
	public static volatile SetAttribute<Job, Descendant> descendants;
	public static volatile SetAttribute<Job, Descendant> currentJobInDescendant;
	public static volatile SingularAttribute<Job, Boolean> alert;
	public static volatile SingularAttribute<Job, String> insightVersions;
	public static volatile SetAttribute<Job, Link> linksWithJobAsParent;
	public static volatile SingularAttribute<Job, Long> id;
	public static volatile SetAttribute<Job, Ancestor> ancestors;

}

