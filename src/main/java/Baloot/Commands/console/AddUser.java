package Baloot.Commands.console;

import Baloot.AcceptMethod;
import Baloot.Commands.Command;
import Baloot.Context.ContextManager;
import Baloot.Entity.User;
import Baloot.Model.UserModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("addUser")
public class AddUser extends Command {
    @AcceptMethod(RequestMethod.GET)
    public String handle(UserModel input) throws Exception {
        User user = new User(input);
        ContextManager.putUser(input.username, user);
        return  "user added successfully";
    }
}
