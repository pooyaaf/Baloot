package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityListModel;
import Baloot.Model.ReportCommodityListModel;
import Baloot.Model.UserByUsernameModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Route("getBuyList")
public class GetBuyList extends Command{
    @AcceptMethod(RequestMethod.GET)
    public ReportCommodityListModel handle(UserByUsernameModel input) throws Exception, UserNotFound {
        User user = ContextManager.getUser(input.username);
        HashSet<Commodity> buyList = user.getBuyList();
        ReportCommodityListModel result = new ReportCommodityListModel();
        result.ReportCommodityList = new ArrayList<>();
        for (Commodity commodity : buyList) {
            result.ReportCommodityList.add(commodity.getReportModel());
        }
        return result;
    }
}
