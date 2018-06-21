package au.com.lifebio.lifebiocontactdetails.contact.dao;

import au.com.lifebio.lifebiocontactdetails.contact.ContactDetailsImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Trevor on 2018/06/20.
 */

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetailsImpl, Long> {
}