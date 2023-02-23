package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Model.BuyModel;
import Baloot.RequestMethod;
import Baloot.Route;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotFound;

@Route("addToBuyList")
public class AddToBuyList extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(BuyModel input) throws Exception, UserNotFound, CommodityNotFound {
        Commodity commodity = ContextManager.getCommodity(input.commodityId);
        User user = ContextManager.getUser(input.username);
        user.addToBuyList(commodity);

        return "commodity was added to buy list successfully";
    }
}
