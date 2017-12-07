package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Acquisition.class)
public abstract class Acquisition_ {

	public static volatile SingularAttribute<Acquisition, Long> firstChannel;
	public static volatile SingularAttribute<Acquisition, Long> gun;
	public static volatile SingularAttribute<Acquisition, Long> lastGoodFFID;
	public static volatile SingularAttribute<Acquisition, Subsurface> subsurfaceFK;
	public static volatile SingularAttribute<Acquisition, Long> firstShot;
	public static volatile SingularAttribute<Acquisition, Long> lgsp;
	public static volatile SingularAttribute<Acquisition, Long> firstFFID;
	public static volatile SingularAttribute<Acquisition, Long> lastFFID;
	public static volatile SingularAttribute<Acquisition, Sequence> sequence;
	public static volatile SingularAttribute<Acquisition, Long> fgsp;
	public static volatile SingularAttribute<Acquisition, Long> lastShot;
	public static volatile SingularAttribute<Acquisition, Long> lastChannel;
	public static volatile SingularAttribute<Acquisition, Long> firstGoodFFID;
	public static volatile SingularAttribute<Acquisition, Long> id;
	public static volatile SingularAttribute<Acquisition, Long> cable;

}

