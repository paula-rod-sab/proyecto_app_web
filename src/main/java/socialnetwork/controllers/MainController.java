package socialnetwork.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import socialnetwork.model.Publication;
import socialnetwork.model.User;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(path = "/")
    public String mainView(Model model) {
        User profileUser = new User();
        profileUser.setName("Mary Jones");
        profileUser.setDescription("Addicted to social networks");
        List<Publication> publications = new ArrayList<Publication>();
        User userJane = new User();
        userJane.setEmail("jane@example.com");
        userJane.setName("Jane Doe");
        userJane.setDescription("I love dogs!");
        User userJohn = new User();
        userJohn.setEmail("john@excample.com");
        userJohn.setName("John Doe");
        userJohn.setDescription("Professional couch potato");
        User userAlice= new User();
        userAlice.setEmail("alice@excample.com");
        userAlice.setName("Alice Tackett");
        userAlice.setDescription("In love!");
        Publication pub1 = new Publication();
        pub1.setUser(userJane);
        pub1.setText("I've published a new tutorial about how to create a Spring MVC app!!!");
        pub1.setRestricted(false);
        pub1.setTimestamp(new Date());
        Publication pub2 = new Publication();
        pub2.setUser(userJohn);
        pub2.setText("Watching TV for 8 hours in a row. It's a new record!!!");
        pub2.setRestricted(true);
        pub2.setTimestamp(new Date());
        Publication pub3 = new Publication();
        pub3.setUser(userAlice);
        pub3.setText("Having dinner with my husband in Hibachi!");
        pub3.setRestricted(true);
        pub3.setTimestamp(new Date());
        publications.add(pub1);
        publications.add(pub2);
        publications.add(pub3);
        model.addAttribute("profileUser", profileUser);
        model.addAttribute("publications", publications);
        return "main_view";
    }

    @GetMapping(path = "/user")
    public String userView(Model model){
        List<Publication> publications = new ArrayList<Publication>();
        User userBob = new User();
        userBob.setEmail("bob@example.com");
        userBob.setName("Bob Ratliff");
        userBob.setDescription("I love basketball!"); 
        Publication pub1 = new Publication();
        pub1.setUser(userBob);
        pub1.setText("Listen to my new song on Spotify!!");
        pub1.setRestricted(false);
        pub1.setTimestamp(new Date());
        Publication pub2 = new Publication();
        pub2.setUser(userBob);
        pub2.setText("Help! my dog is soo adorable");
        pub2.setRestricted(true);
        pub2.setTimestamp(new Date());
        Publication pub3 = new Publication();
        pub3.setUser(userBob);
        pub3.setText("Dinner with Alice in Hibachi!");
        pub3.setRestricted(true);
        pub3.setTimestamp(new Date());
        publications.add(pub1);
        publications.add(pub2);
        publications.add(pub3);
        model.addAttribute("user", userBob);
        model.addAttribute("publications", publications);

        return "user_view";
    } 

    @GetMapping(path = "/login")
    public String loginForm() {
        return "login";
    }

}
