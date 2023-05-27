package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.View.BuyListModel;
import Baloot.RequestMethod;
import Baloot.Route;
import Baloot.View.UserInfoModel;

import java.util.ArrayList;
import java.util.Collection;

@Route("getBuyList")
public class GetBuyList extends Command{
    @AcceptMethod(RequestMethod.GET)
    public UserInfoModel handle(String username) throws Exception, UserNotFound {
        User user = ContextManager.getInstance().getUser(username);
        return null;
    }
}
