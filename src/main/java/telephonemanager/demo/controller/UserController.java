package telephonemanager.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import telephonemanager.demo.helper.Message;
import telephonemanager.demo.model.Contact;
import telephonemanager.demo.model.User;
import telephonemanager.demo.repository.ContactRepository;
import telephonemanager.demo.repository.UserRepository;
import telephonemanager.demo.service.ContactService;
import telephonemanager.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {

        model.addAttribute("user", userService.getUser(principal));
        return "norm/userDashboard";
    }

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        model.addAttribute("user", userService.getUser(principal));
    }

    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());
        return "norm/addContact";
    }

    @PostMapping("/process-contact")
    public String processContact(
            @ModelAttribute Contact contact,
            Principal principal,
            HttpSession session) {

        try {

            User user = userService.addContact(contact, principal);
            contact.setUser(user);


            userService.saveUser(user);

            session.setAttribute("message", new Message("Contact Added Successfully!", "success"));


        } catch (Exception e) {
            System.out.println("ERROR!" + e.getMessage());
            e.printStackTrace();

            session.setAttribute("message", new Message("Something Went Wrong!", "danger"));
        }
        return "norm/addContact";
    }


    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model m, Principal principal) {
        m.addAttribute("title", "View Contacts");
        Pageable pageable = PageRequest.of(page, 3);
        Page<Contact> contacts = contactService.findContactsByUser(userService.getUser(principal).getId(), pageable);
        m.addAttribute("contacts", contacts);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", contacts.getTotalPages());

        return "norm/showContacts";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") Integer id, Model model, HttpSession session,
                                Principal principal) {

        userService.saveUser(userService.deleteContact(contactService.findById(id), principal));

        session.setAttribute("message", new Message("Contact deleted succesfully...", "success"));

        return "redirect:/user/show-contacts/0";
    }

    @PostMapping("/update-contact/{id}")
    public String updateForm(@PathVariable("id") Integer id, Model m) {

        m.addAttribute("title", "Update Contact");

        Contact contact = contactService.findById(id);

        m.addAttribute("contact", contact);

        return "norm/updateContact";
    }

    @RequestMapping(value = "/process-update", method = RequestMethod.POST)
    public String updateHandler(@ModelAttribute Contact contact,
                                Model m, HttpSession session, Principal principal) {

        try {

            contact.setUser(userService.getUser(principal));

            contactService.saveContact(contact);

            session.setAttribute("message", new Message("Your contact is updated...", "success"));

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Your contact is not updated...", "danger"));

        }


        return "redirect:/user/show-contacts/0";
    }

    @GetMapping("/profile")
    public String yourProfile(Model model, Principal principal) {
        model.addAttribute("title", "Profile");
        model.addAttribute("user", userService.getUser(principal));

        return "norm/profile";
    }

    @GetMapping("/find-contact")
    public String findContactForm(Model model) {
        model.addAttribute("title", "Find Contact");
        model.addAttribute("contact", new Contact());
        return "norm/findContact";
    }

    @PostMapping("/process-find")
    public String findContact(
            @ModelAttribute Contact contact,
            Principal principal,
            HttpSession session, Model model) {
        HashSet<Contact> contacts= new HashSet<>();
        User user = userService.getUser(principal);
        System.out.println(contact.getName());
        System.out.println(contact.getNumberPhone());

            if (!contact.getName().isEmpty()) {
                user.getContacts().stream().filter(contact1 -> contact1.getName().equals(contact.getName())).forEach(contact1 ->contacts.add(contact1));
            }
            if (!contact.getNumberPhone().isEmpty()) {
                user.getContacts().stream().filter(contact1 -> contact1.getNumberPhone().equals(contact.getNumberPhone())).forEach(contact1 ->contacts.add(contact1));
            }

        model.addAttribute("contacts", contacts);

        return "norm/showFindContact";
        }

    }
