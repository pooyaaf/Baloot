package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.CreditNotEnough;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Model.UserByIdModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.view.UserInfoModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("payment/{user_id}")
public class Payment extends Command {
    public UserInfoModel handle(UserByIdModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        User user = ContextManager.getUser(input.user_id);
        try {
            user.payment();
        } catch (CreditNotEnough e) {
            e.printStackTrace();
            return user.getUserInfoModel();
        }

        return user.getUserInfoModel();
    }

    @AcceptMethod(RequestMethod.GET)
    public UserInfoModel getHandle(UserByIdModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        return handle(input);
    }

    @AcceptMethod(RequestMethod.POST)
    public UserInfoModel postHandle(UserByIdModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        return handle(input);
    }
}