package Baloot.Exception;

public class UserNotAuthenticated extends Throwable {
    public String getMessage() {
        return "UserNotAuthenticated";
    }
}
