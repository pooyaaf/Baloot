package Baloot.Controllers;

import Baloot.Context.UserContext;

public class Authentication {
    public static boolean isNotAuthenticated() {
        if (UserContext.username == null) {
            return true;
        }
        return false;
    }
}
