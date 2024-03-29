package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Discount;
import Baloot.Entity.User;
import Baloot.Exception.DiscountNotFound;
import Baloot.Exception.ExpiredDiscount;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.DiscountModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/addDiscount")
public class AddDiscountController {
    @GetMapping
    public DiscountModel addDiscount(@RequestParam("discount") String discount_amount) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            User user = ContextManager.getInstance().getUser(UserContext.username);
            Discount discount = ContextManager.getInstance().getDiscount(discount_amount);
            user.addDiscount(discount);
            return discount.getModel();
        }
        catch (Exception | UserNotFound | DiscountNotFound | ExpiredDiscount | UserNotAuthenticated e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
