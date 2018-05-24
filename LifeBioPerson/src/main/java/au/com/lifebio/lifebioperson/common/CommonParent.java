package au.com.lifebio.lifebioperson.common;

/**
 * The <code>CommonParent</code> is used as a 'container' to
 * represent a objects state.
 *
 *
 * @author <a href="mailto:inboxtrevorjohnson@gmail.com">ALR</a>
 */
public interface CommonParent {

    void setOID(Long oID);

    Long getOID();

    void setLastModified(Long lastModified);

    Long getLastModified();
}
