package Baloot.Controllers;

import Baloot.Commands.GetBuyList;
import Baloot.Context.ContextManager;
import Baloot.Context.Filter.FilterManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Commodity;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Repository.BuyListRepository;
import Baloot.View.CommodityListModel;
import Baloot.View.UserInfoModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/buyList")
public class BuyListController {
    @Autowired
    BuyListRepository repository;
    @GetMapping
    public UserInfoModel all() {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            GetBuyList command = new GetBuyList();
            return command.handle(UserContext.username);
        }
        catch (UserNotFound | UserNotAuthenticated | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
