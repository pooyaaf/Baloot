package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Model.CommodityByIdModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addToBuyList/{username}/{commodityId}")
public class AddCommodityToBuyList extends Command{
    public CommodityShortModel handle(CommodityBuyListModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        Commodity commodity = ContextManager.getCommodity(Integer.parseInt(input.commodityId));
        User user = ContextManager.getUser(input.username);
        commodity.decreaseInStuck();
        user.addToBuyList(commodity);

        return commodity.getReportModel();
    }

    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel getHandle(CommodityBuyListModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        return handle(input);
    }

    @AcceptMethod(RequestMethod.POST)
    public CommodityShortModel postHandle(CommodityBuyListModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        return handle(input);
    }
}