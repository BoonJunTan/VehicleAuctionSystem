package ejb;

import ejb.VehicleEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-27T11:23:51")
@StaticMetamodel(ModelEntity.class)
public class ModelEntity_ { 

    public static volatile SingularAttribute<ModelEntity, Integer> id;
    public static volatile SingularAttribute<ModelEntity, String> model;
    public static volatile SingularAttribute<ModelEntity, Integer> manufacturedYear;
    public static volatile CollectionAttribute<ModelEntity, VehicleEntity> vehicles;
    public static volatile SingularAttribute<ModelEntity, String> make;

}