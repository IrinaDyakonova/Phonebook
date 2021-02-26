package telephonemanager.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import telephonemanager.demo.model.User;
import telephonemanager.demo.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByUserName = userRepository.getUserByUserEmail(email);
        if (userByUserName == null) {
            throw new UsernameNotFoundException("Could not found user.");
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(userByUserName);
        return customUserDetails;
    }
}
