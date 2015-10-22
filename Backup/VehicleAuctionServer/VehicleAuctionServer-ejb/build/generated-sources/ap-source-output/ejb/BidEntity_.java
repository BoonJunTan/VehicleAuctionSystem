package ejb;

import ejb.UserEntity;
import ejb.VehicleEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-22T19:25:05")
@StaticMetamodel(BidEntity.class)
public class BidEntity_ { 

    public static volatile SingularAttribute<BidEntity, Long> id;
    public static volatile SingularAttribute<BidEntity, String> bidAmount;
    public static volatile SingularAttribute<BidEntity, VehicleEntity> vehicle;
    public static volatile SingularAttribute<BidEntity, UserEntity> user;
    public static volatile SingularAttribute<BidEntity, Date> bidTime;

}