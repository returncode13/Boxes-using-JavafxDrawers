package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Dot.class)
public abstract class Dot_ {

	public static volatile SetAttribute<Dot, Doubt> doubts;
	public static volatile SingularAttribute<Dot, Workspace> workspace;
	public static volatile SetAttribute<Dot, VariableArgument> variableArguments;
	public static volatile SingularAttribute<Dot, String> function;
	public static volatile SetAttribute<Dot, Link> links;
	public static volatile SingularAttribute<Dot, Long> id;
	public static volatile SingularAttribute<Dot, Double> error;
	public static volatile SingularAttribute<Dot, Double> tolerance;
	public static volatile SingularAttribute<Dot, String> status;

}

