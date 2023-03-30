package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.Model.UserByIdModel;
import Baloot.View.UserInfoModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("users/{user_id}")
public class GetUserById extends Command{
    @AcceptMethod(RequestMethod.GET)
    public UserInfoModel handle(UserByIdModel input) throws Exception, UserNotFound {
        User user = ContextManager.getInstance().getUser(input.user_id);
        return user.getUserInfoModel();
    }
}
