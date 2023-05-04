package Baloot.Commands;

import Baloot.Context.ContextManager;
import Baloot.Entity.User;
import Baloot.Model.UserModel;

public class AddUser extends Command {
    public void handle(UserModel input) throws Exception {
        User user = new User(input);
        ContextManager.getInstance().putUser(input.username, user);
    }
}
