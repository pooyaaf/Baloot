package Baloot.Entity;

import Baloot.Model.UserModel;
import lombok.Getter;
import lombok.Setter;

public class User {
    @Setter @Getter
    private String username;
    @Setter @Getter
    private String password;
    @Setter @Getter
    private String email;
    @Setter @Getter
    private String birthDate;
    @Setter @Getter
    private String address;
    @Setter @Getter
    private int credit;

    public User(UserModel model) {
        super();
        username = model.username;
        password = model.password;
        email = model.email;
        birthDate = model.birthDate;
        address = model.address;
        credit = model.credit;
    }
}
