package ejb;

import ejb.UserEntity;
import ejb.VehicleEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-22T19:25:05")
@StaticMetamodel(PaymentEntity.class)
public class PaymentEntity_ { 

    public static volatile SingularAttribute<PaymentEntity, Long> id;
    public static volatile SingularAttribute<PaymentEntity, String> paymentAmount;
    public static volatile SingularAttribute<PaymentEntity, String> cardHolderName;
    public static volatile SingularAttribute<PaymentEntity, Date> paymentTime;
    public static volatile SingularAttribute<PaymentEntity, VehicleEntity> vehicle;
    public static volatile SingularAttribute<PaymentEntity, String> cardType;
    public static volatile SingularAttribute<PaymentEntity, UserEntity> user;
    public static volatile SingularAttribute<PaymentEntity, String> cardNumber;

}