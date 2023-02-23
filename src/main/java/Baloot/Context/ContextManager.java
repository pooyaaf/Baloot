package Baloot.Context;

import Baloot.Entity.User;
import Baloot.Exception.UserNotFound;

import java.util.HashMap;

public class ContextManager {
    private static HashMap<String, User> users = new HashMap<>();

    public static void putUser(String username, User user) {
        users.put(username, user);
    }

    public static User getUser(String username) throws Exception, UserNotFound {
        if (!users.containsKey(username)) {
            throw new UserNotFound();
        }
        return users.get(username);
    }
}
