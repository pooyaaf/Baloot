package Baloot.Controllers;

import Baloot.Commands.AddUser;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    @PostMapping
    public void register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("birthdate") String birthdate, @RequestParam("address") String address) {
        if (ContextManager.getInstance().isUserPassExist(username, password)) {
            UserContext.username = username;
            return;
        }
        UserModel model = new UserModel();
        model.username = username;
        model.password = password;
        model.email = email;
        model.birthDate = birthdate;
        model.address = address;
        AddUser addUser = new AddUser();
        try {
            addUser.handle(model);
            UserContext.username = username;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

