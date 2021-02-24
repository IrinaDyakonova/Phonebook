package telephonemanager.demo.service;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telephonemanager.demo.model.Contact;
import telephonemanager.demo.model.User;
import telephonemanager.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getUser(Principal principal){
        String email = principal.getName();
        User userByEmail = userRepository.getUserByUserEmail(email);
        return userByEmail;
    }

    public User addContact(Contact contact, Principal principal) {
        this.getUser(principal).getContacts().add(contact);
        return getUser(principal);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
