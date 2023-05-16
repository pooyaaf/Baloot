package Baloot.Controllers;

import Baloot.Context.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/logout")
public class LogoutController {
    @PostMapping
    public void logout() {
        UserContext.username = null;
    }
}
