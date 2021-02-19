package telephonemanager.demo.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import telephonemanager.demo.model.User;
import telephonemanager.demo.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){
        String email = principal.getName();
        User userByEmail = userRepository.getUserByUserEmail(email);
        model.addAttribute("user", userByEmail);
        return "norm/userDashboard";
    }
}
