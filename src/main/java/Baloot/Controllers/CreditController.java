package Baloot.Controllers;


import Baloot.Commands.AddCredit;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.AddCreditModel;
import lombok.AllArgsConstructor;
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
    @PostMapping
    public void addCredit(@RequestParam("credit") String credit) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            AddCreditModel addCreditModel = new AddCreditModel();
            addCreditModel.user_id = UserContext.username;
            addCreditModel.credit = credit;
            AddCredit command = new AddCredit();
            command.handle(addCreditModel);
        } catch (UserNotFound | UserNotAuthenticated | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
