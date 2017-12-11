package db.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PropertyType.class)
public abstract class PropertyType_ {

	public static volatile SingularAttribute<PropertyType, Long> idNodeType;
	public static volatile SingularAttribute<PropertyType, String> name;
	public static volatile SetAttribute<PropertyType, NodeProperty> nodePropertyType;

}

