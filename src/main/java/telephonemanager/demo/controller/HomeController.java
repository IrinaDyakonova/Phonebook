package telephonemanager.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import telephonemanager.demo.helper.Message;
import telephonemanager.demo.model.User;
import telephonemanager.demo.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    
    @RequestMapping("/")
   public String home(Model model) {
        model.addAttribute("title","Home - Phonebook");
       return "home";
   }



    @RequestMapping("/signin")
    public String mylogin(Model model) {
        model.addAttribute("title","Login - Phonebook");
        return "login";
    }


    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title","Register - Phonebook");
        model.addAttribute("user", new User());
        return "signup";
    }


    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result ,@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {
       try {
           if (!agreement) {
               throw new Exception("You have not agreed the terms and conditions");
           }
           if (result.hasErrors()) {

               System.out.println(result.toString());
               model.addAttribute("user",user);
               return "signup";
           }
           user.setRole("ROLE_USER");
           user.setEnabled(true);
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           User saveResult = userService.saveUser(user);
           model.addAttribute("user", new User());
           session.setAttribute("message", new Message("Successfully registered.", "alert-success"));
           return "signup";

       } catch (Exception e){
           e.printStackTrace();
           model.addAttribute("user", user);
           session.setAttribute("message", new Message("Something went wrong. ", "alert-danger"));
           return "signup";

       }

    }
}
