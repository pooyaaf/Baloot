package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;
import Baloot.Model.AddCreditModel;
import Baloot.View.UserInfoModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addCredit/{user_id}/{credit}")
public class AddCredit extends Command {
    @AcceptMethod(RequestMethod.GET)
    public UserInfoModel handle(AddCreditModel input) throws Exception, UserNotFound {
        return null;

    }
}
