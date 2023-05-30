package Baloot.Controllers;

import Baloot.Commands.GetBuyList;
import Baloot.Context.ContextManager;
import Baloot.Context.Filter.FilterManager;
import Baloot.Context.UserContext;
import Baloot.Entity.BuyList;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.UserNotAuthenticated;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityModel;
import Baloot.Repository.BuyListRepository;
import Baloot.Repository.PurchasedListRepository;
import Baloot.View.CommodityListModel;
import Baloot.View.UserInfoModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/buyList")
public class BuyListController {
    @Autowired
    private final BuyListRepository repository;

    @Autowired
    private final PurchasedListRepository purchasedListRepository;

    ArrayList<CommodityModel> getBuyList(User user) {
        List<BuyList> buyLists = (List<BuyList>) repository.findAllByBuyListId_User(user);
        ArrayList<CommodityModel> model = new ArrayList<CommodityModel>();
        for (BuyList buyList : buyLists) {
            CommodityModel commodityModel = buyList.getCommodity().getModel();
            commodityModel.inCart = buyList.getInStock();
            model.add(commodityModel);
        }
        return model;
    }
    @GetMapping
    public UserInfoModel all() {
        try {
            User user = ContextManager.getInstance().getUser(UserContext.username);
            UserInfoModel userInfoModel = user.getUserInfoModel(repository.findAllByBuyListId_User(user), purchasedListRepository.findAllByPurchasedListId_User(user));
            userInfoModel.buyList = getBuyList(user);
            return userInfoModel;
        }
        catch (UserNotFound | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
