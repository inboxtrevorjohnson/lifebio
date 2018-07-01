package au.com.lifebio.lifebiocommon.common;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by Trevor on 2018/06/18.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class CommonParentImpl implements CommonParent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long oID;
    @NotNull
    protected LocalDateTime lastModified;

    @Override
    public void setOID(Long oID) {
        this.oID = oID;
    }

    @Override
    public Long getOID() {
        return oID;
    }

    @Override
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public LocalDateTime getLastModified() {
        return lastModified;
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof CommonParent && this.oID != null) {
            return ((CommonParent) object).getOID().equals(this.oID);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this.oID == null) {
            return 0;
        }
        return this.oID.intValue();
    }

}
