package ejb;

import ejb.VehicleEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-27T11:23:51")
@StaticMetamodel(CertificationEntity.class)
public class CertificationEntity_ { 

    public static volatile SingularAttribute<CertificationEntity, Long> id;
    public static volatile SingularAttribute<CertificationEntity, Date> certificationTime;
    public static volatile SingularAttribute<CertificationEntity, String> status;
    public static volatile SingularAttribute<CertificationEntity, String> certificationContent;
    public static volatile SingularAttribute<CertificationEntity, VehicleEntity> vehicle;
    public static volatile SingularAttribute<CertificationEntity, String> certiferName;

}