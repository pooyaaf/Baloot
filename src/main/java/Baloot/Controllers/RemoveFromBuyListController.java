package Baloot.Controllers;

import Baloot.Commands.RateCommodity;
import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.UserContext;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityBuyListModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/removeFromBuyList")
public class RemoveFromBuyListController {
    @PostMapping
    public void removeFromBuyList(@RequestParam("commodityId") String commodityId) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            RemoveFromBuyList removeFromBuyList = new RemoveFromBuyList();
            CommodityBuyListModel model = new CommodityBuyListModel();
            model.username = UserContext.username;
            model.commodityId = commodityId;
            removeFromBuyList.handle(model);
        } catch (UserNotFound | UserNotAuthenticated | CommodityNotFound | CommodityIsNotInBuyList | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
