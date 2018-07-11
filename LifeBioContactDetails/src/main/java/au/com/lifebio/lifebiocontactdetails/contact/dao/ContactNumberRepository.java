package au.com.lifebio.lifebiocontactdetails.contact.dao;

import au.com.lifebio.lifebiocontactdetails.contact.model.ContactNumberImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Trevor on 2018/06/20.
 */

@Repository
public interface ContactNumberRepository extends JpaRepository<ContactNumberImpl, Long> {
}