package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityByIdModel;
import Baloot.Model.UserByIdModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.view.UserInfoModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("users/{user_id}")
public class GetUserById extends Command{
    @AcceptMethod(RequestMethod.GET)
    public UserInfoModel handle(UserByIdModel input) throws Exception, UserNotFound {
        User user = ContextManager.getUser(input.user_id);
        return user.getUserInfoModel();
    }
}
