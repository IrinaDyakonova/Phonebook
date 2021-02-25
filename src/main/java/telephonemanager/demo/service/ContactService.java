package telephonemanager.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import telephonemanager.demo.model.Contact;
import telephonemanager.demo.repository.ContactRepository;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Page<Contact> findContactsByUser(int userId, Pageable pageable) {
       return contactRepository.findContactsByUser(userId, pageable);

    }

    public Contact findById(int contactId) {
        return contactRepository.findById(contactId).get();
    }

    public Contact saveContact(Contact contact) {
        return  contactRepository.save(contact);
    }
}
