package ejb;

import ejb.BidEntity;
import ejb.PaymentEntity;
import ejb.VehicleEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-27T11:23:51")
@StaticMetamodel(UserEntity.class)
public class UserEntity_ { 

    public static volatile SingularAttribute<UserEntity, String> contactNumber;
    public static volatile SingularAttribute<UserEntity, String> email;
    public static volatile SingularAttribute<UserEntity, String> name;
    public static volatile CollectionAttribute<UserEntity, PaymentEntity> payments;
    public static volatile CollectionAttribute<UserEntity, BidEntity> bids;
    public static volatile CollectionAttribute<UserEntity, VehicleEntity> vehicles;
    public static volatile SingularAttribute<UserEntity, String> password;

}