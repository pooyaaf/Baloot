package Baloot.Controllers;

import Baloot.Commands.AddCommodityToBuyList;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Repository.BuyListRepository;
import Baloot.Repository.CommodityRepository;
import Baloot.service.BuyListService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final CommodityRepository commodityRepository;
    @Autowired
    private final BuyListService buyListService;
    @PostMapping("/{commodityId}")
    public void addToBuyList(@PathVariable String commodityId) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            Commodity commodity = ContextManager.getInstance().getCommodity(Integer.parseInt(commodityId));
            User user = ContextManager.getInstance().getUser(UserContext.username);
            commodity.decreaseInStuck();
            commodityRepository.save(commodity);
//            user.addToBuyList(commodity);
            user.setBuyListPrice(user.calculatePayment(buyListService.getBuyList(user)));
            buyListService.addBuyList(user, commodity);
        } catch (UserNotFound | CommodityNotFound | CommodityNotInStuck | UserNotAuthenticated | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
