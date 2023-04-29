package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    @PostMapping
    public void login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (ContextManager.getInstance().isUserPassExist(username, password)) {
            UserContext.username = username;
        }
    }
}