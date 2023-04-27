package Baloot.Controllers;

import Baloot.Commands.AddCredit;
import Baloot.Commands.Payment;
import Baloot.Context.UserContext;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.AddCreditModel;
import Baloot.Model.UserByIdModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    @PostMapping
    public void payment() {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            Payment command = new Payment();
            UserByIdModel model = new UserByIdModel();
            model.user_id = UserContext.username;
            command.handle(model);
        } catch (UserNotFound | CommodityNotFound | CommodityNotInStuck | UserNotAuthenticated | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
