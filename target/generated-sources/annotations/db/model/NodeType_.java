package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NodeType.class)
public abstract class NodeType_ {

	public static volatile SingularAttribute<NodeType, Long> idNodeType;
	public static volatile SingularAttribute<NodeType, String> name;
	public static volatile SingularAttribute<NodeType, Long> actualnodeid;
	public static volatile SetAttribute<NodeType, NodeProperty> nodePropertyType;
	public static volatile SetAttribute<NodeType, Job> jobsteps;

}

