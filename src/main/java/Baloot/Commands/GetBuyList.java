package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.View.BuyListModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;

@Route("getBuyList")
public class GetBuyList extends Command{
    @AcceptMethod(RequestMethod.GET)
    public BuyListModel handle(String username) throws Exception, UserNotFound {
        User user = ContextManager.getInstance().getUser(username);
        Collection<Commodity> buyList = user.getBuyList();
        BuyListModel result = new BuyListModel();
        result.user = user.getUserInfoModel().userModel;
        result.buyList = new ArrayList<>();
        for (Commodity commodity : buyList) {
            result.buyList.add(commodity.getReportModel());
        }
        return result;
    }
}
