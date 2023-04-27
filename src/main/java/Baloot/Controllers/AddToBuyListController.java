package Baloot.Controllers;

import Baloot.Commands.AddCommodityToBuyList;
import Baloot.Context.UserContext;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityBuyListModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/addToBuyList")
public class AddToBuyListController {
    @PostMapping("/{commodityId}")
    public void addToBuyList(@PathVariable String commodityId) {
        if (Authentication.isNotAuthenticated()) return;
        try {
            AddCommodityToBuyList command = new AddCommodityToBuyList();
            CommodityBuyListModel model = new CommodityBuyListModel();
            model.commodityId = commodityId;
            model.username = UserContext.username;
            command.handle(model);
        } catch (UserNotFound | CommodityNotFound | CommodityNotInStuck | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
