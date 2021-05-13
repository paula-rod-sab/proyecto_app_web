package socialnetwork.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;

import socialnetwork.model.Publication;
import socialnetwork.model.User;
import socialnetwork.model.PublicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import socialnetwork.services.UserService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import socialnetwork.model.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(path = "/")
    public String mainView(Model model, Principal principal, Publication publication) {
        User profileUser = userRepository.findByEmail(principal.getName());
        model.addAttribute("user", profileUser);
        profileUser.setName(profileUser.getName());
        profileUser.setDescription(profileUser.getDescription());
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("publications", publicationRepository.findFirst10ByRestrictedIsFalseOrderByTimestampDesc());
        return "main_view";
    }

    @GetMapping(path = "/user/{userId}")
    public String userView(@PathVariable int userId, Model model) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        }
        User user = userOpt.get();
        model.addAttribute("user", user);
        model.addAttribute("publications", publicationRepository.findByUserOrderByTimestampDesc(user));
        return "user_view";
    }

    @GetMapping(path = "/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping(path = "/register")
    public String registerForm(User user) {
        return "register";
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PublicationRepository publicationRepository;    

    @PostMapping(path = "/register")
    public String register(@Valid @ModelAttribute("user") User user,
                        BindingResult bindingResult,
                        @RequestParam String passwordRepeat) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "redirect:register?duplicate_email";
        }
        if (user.getPassword().equals(passwordRepeat)) {
            userService.register(user);
        } else {
            return "redirect:register?passwords";
        }
        return "redirect:login?registered";
    }

    @PostMapping(path = "/post")
    public String postPublication(@Valid @ModelAttribute("publication") Publication publication,
                                BindingResult bindingResult,
                                Principal principal) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        User user = userRepository.findByEmail(principal.getName());
        publication.setUser(user);
        publication.setTimestamp(new Date());
        publicationRepository.save(publication);
        return "redirect:/";
    }

    @PostMapping(path = "/bio")
    public String createbio(@Valid @ModelAttribute("user") User user,
                            BindingResult bindingResult, @RequestParam String description, 
                            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "redirect:/user";
        }

        user.setDescription(description);
        userRepository.save(user);
        
        
        return "redirect:/user";
    }

     

}
