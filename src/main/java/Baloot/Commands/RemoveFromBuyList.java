package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.BuyModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("removeFromBuyList")
public class RemoveFromBuyList extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(BuyModel input) throws Exception, UserNotFound, CommodityNotFound, CommodityIsNotInBuyList {
        Commodity commodity = ContextManager.getCommodity(input.commodityId);
        User user = ContextManager.getUser(input.username);
        commodity.increaseInStuck();
        user.removeFromBuyList(commodity);

        return "commodity was removed to buy list successfully";
    }
}
