package Baloot.Context;

import Baloot.Entity.Provider;
import Baloot.Entity.User;
import Baloot.Exception.ProviderNotFound;
import Baloot.Exception.UserNotFound;

import java.util.HashMap;

public class ContextManager {
    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<Integer, Provider> providers = new HashMap<>();

    public static void putUser(String username, User user) {
        users.put(username, user);
    }

    public static User getUser(String username) throws Exception, UserNotFound {
        if (!users.containsKey(username)) {
            throw new UserNotFound();
        }
        return users.get(username);
    }
    public static void putProvider(Integer id, Provider provider) {
        providers.put(id, provider);
    }

    public static Provider getProvider(Integer id) throws Exception, ProviderNotFound {
        if (!providers.containsKey(id)) {
            throw new ProviderNotFound();
        }
        return providers.get(id);
    }
}
