package au.com.lifebio.lifebioperson.common;

/**
 * The <code>CommonParentImpl</code> is used as a 'container' to
 * represent a persons state.
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */

public abstract class CommonParentImpl implements CommonParent {
    protected Long oID, lastModified;

    @Override
    public void setOID(Long oID) {
        this.oID = oID;
    }

    @Override
    public Long getOID() {
        return oID;
    }

    @Override
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public Long getLastModified() {
        return lastModified;
    }
}
