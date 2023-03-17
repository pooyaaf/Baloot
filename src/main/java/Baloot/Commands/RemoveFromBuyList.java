package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.BuyModel;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("removeFromBuyList/{username}/{commodityId}")
public class RemoveFromBuyList extends Command {
    public CommodityShortModel handle(CommodityBuyListModel input) throws Exception, UserNotFound, CommodityNotFound, CommodityIsNotInBuyList {
        Commodity commodity = ContextManager.getCommodity(Integer.parseInt(input.commodityId));
        User user = ContextManager.getUser(input.username);
        commodity.increaseInStuck();
        user.removeFromBuyList(commodity);
        return commodity.getReportModel();
    }

    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel getHandle(CommodityBuyListModel input) throws Exception, UserNotFound, CommodityNotFound, CommodityIsNotInBuyList {
        return handle(input);
    }

    @AcceptMethod(RequestMethod.POST)
    public CommodityShortModel postHandle(CommodityBuyListModel input) throws Exception, UserNotFound, CommodityNotFound, CommodityIsNotInBuyList {
        return handle(input);
    }
}
