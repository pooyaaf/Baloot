package Baloot.Controllers;


import Baloot.Commands.AddCredit;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.AddCreditModel;
import Baloot.Repository.CommodityRepository;
import Baloot.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    private final UserRepository userRepository;
    @PostMapping
    public void addCredit(@RequestParam("credit") String credit) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            User user = ContextManager.getInstance().getUser(UserContext.username);
            user.addCredit(Integer.parseInt(credit));
            userRepository.save(user);
        } catch (UserNotFound | UserNotAuthenticated | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
