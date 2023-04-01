package Baloot.Model;

import Baloot.Validation.Username;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class UserModel {
    @Username
    public String username;
    public String password;
    public String email;
    public String birthDate;
    public String address;
    public int credit;

}
