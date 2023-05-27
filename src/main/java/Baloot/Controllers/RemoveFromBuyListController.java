package Baloot.Controllers;

import Baloot.Commands.RateCommodity;
import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Repository.CommodityRepository;
import Baloot.service.BuyListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/removeFromBuyList")
public class RemoveFromBuyListController {
    @Autowired
    private final BuyListService buyListService;
    @Autowired
    private final CommodityRepository commodityRepository;
    @PostMapping
    public void removeFromBuyList(@RequestParam("commodityId") String commodityId) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            Commodity commodity = ContextManager.getInstance().getCommodity(Integer.parseInt(commodityId));
            User user = ContextManager.getInstance().getUser(UserContext.username);
            commodity.increaseInStuck();
            commodityRepository.save(commodity);
            buyListService.removeFromBuyList(user, commodity);
        } catch (UserNotFound | UserNotAuthenticated | CommodityNotFound | CommodityIsNotInBuyList | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
