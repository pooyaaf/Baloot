package Baloot.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidation {
    public static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9]+$");

    public static boolean userValidation(String userString) {
        Matcher matcher = VALID_USERNAME_REGEX.matcher(userString);
        return matcher.find();
    }

}
