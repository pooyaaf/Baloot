package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (ContextManager.getInstance().isUserPassExist(username, password)) {
            UserContext.username = username;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username and password");
        }
    }
}
