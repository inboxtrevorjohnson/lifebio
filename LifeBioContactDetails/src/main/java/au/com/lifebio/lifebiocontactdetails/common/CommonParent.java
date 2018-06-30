package au.com.lifebio.lifebiocontactdetails.common;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Trevor on 2018/06/18.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public interface CommonParent {

    void setOID(Long oID);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long getOID();

    void setLastModified(LocalDateTime lastModified);

    LocalDateTime getLastModified();
}
