package au.com.lifebio.lifebiocontactdetails.common;

import java.time.LocalDateTime;

/**
 * Created by Trevor on 2018/06/18.
 */
public interface CommonParent {

    void setOID(Long oID);

    Long getOID();

    void setLastModified(LocalDateTime lastModified);

    LocalDateTime getLastModified();
}
