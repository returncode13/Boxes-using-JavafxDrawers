package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Sequence.class)
public abstract class Sequence_ {

	public static volatile SetAttribute<Sequence, Doubt> doubts;
	public static volatile SingularAttribute<Sequence, Long> sequenceno;
	public static volatile SingularAttribute<Sequence, String> realLineName;
	public static volatile SetAttribute<Sequence, Subsurface> subsurfaces;
	public static volatile SingularAttribute<Sequence, Long> id;
	public static volatile SetAttribute<Sequence, Acquisition> acquisition;
	public static volatile SingularAttribute<Sequence, String> status;
	public static volatile SetAttribute<Sequence, Summary> summaries;

}

