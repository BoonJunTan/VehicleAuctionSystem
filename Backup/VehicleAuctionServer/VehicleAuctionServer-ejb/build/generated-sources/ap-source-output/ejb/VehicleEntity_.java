package ejb;

import ejb.BidEntity;
import ejb.CertificationEntity;
import ejb.ModelEntity;
import ejb.PaymentEntity;
import ejb.UserEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-27T11:23:51")
@StaticMetamodel(VehicleEntity.class)
public class VehicleEntity_ { 

    public static volatile SingularAttribute<VehicleEntity, CertificationEntity> certification;
    public static volatile SingularAttribute<VehicleEntity, String> registrationNumber;
    public static volatile SingularAttribute<VehicleEntity, ModelEntity> model;
    public static volatile SingularAttribute<VehicleEntity, Date> auctionStartTime;
    public static volatile SingularAttribute<VehicleEntity, Long> vehicleId;
    public static volatile SingularAttribute<VehicleEntity, String> description;
    public static volatile CollectionAttribute<VehicleEntity, PaymentEntity> payments;
    public static volatile SingularAttribute<VehicleEntity, String> chassisNumber;
    public static volatile SingularAttribute<VehicleEntity, Date> auctionEndTime;
    public static volatile SingularAttribute<VehicleEntity, String> startingBid;
    public static volatile CollectionAttribute<VehicleEntity, BidEntity> bids;
    public static volatile SingularAttribute<VehicleEntity, UserEntity> user;
    public static volatile SingularAttribute<VehicleEntity, String> engineNumber;

}