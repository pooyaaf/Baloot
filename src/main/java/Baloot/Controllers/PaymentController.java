package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.BuyList;
import Baloot.Entity.User;
import Baloot.Exception.*;
import Baloot.Model.AddCreditModel;
import Baloot.Model.UserByIdModel;
import Baloot.Repository.BuyListRepository;
import Baloot.service.BuyListService;
import Baloot.service.PurchasedListService;
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
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private final BuyListService buyListService;

    @Autowired
    private final PurchasedListService purchasedListService;
    @PostMapping
    public void payment() {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            User user = ContextManager.getInstance().getUser(UserContext.username);
            Iterable<BuyList> buyLists = buyListService.getBuyList(user);
            user.payment(buyLists);
            purchasedListService.addPurchasedList(buyLists);
            buyListService.clearUserBuyList(user);
        } catch (UserNotFound | UserNotAuthenticated | CreditNotEnough | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
