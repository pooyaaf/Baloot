package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.Model.AddCreditModel;
import Baloot.Model.view.UserInfoModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addCredit/{user_id}/{credit}")
public class AddCredit extends Command {
    @AcceptMethod(RequestMethod.GET)
    public UserInfoModel handle(AddCreditModel input) throws Exception, UserNotFound {
        User user = ContextManager.getUser(input.user_id);
        user.addCredit(Integer.parseInt(input.credit));
        return user.getUserInfoModel();
    }
}
