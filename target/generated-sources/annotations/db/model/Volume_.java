package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Volume.class)
public abstract class Volume_ {

	public static volatile SingularAttribute<Volume, Long> volumeType;
	public static volatile SetAttribute<Volume, Header> headers;
	public static volatile SingularAttribute<Volume, String> nameVolume;
	public static volatile SingularAttribute<Volume, String> md5Hash;
	public static volatile SingularAttribute<Volume, Boolean> alert;
	public static volatile SingularAttribute<Volume, String> pathOfVolume;
	public static volatile SingularAttribute<Volume, Long> id;
	public static volatile SingularAttribute<Volume, Job> job;
	public static volatile SingularAttribute<Volume, Boolean> headerExtracted;

}

