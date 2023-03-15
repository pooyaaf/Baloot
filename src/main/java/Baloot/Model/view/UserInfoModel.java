package Baloot.Model.view;

import Baloot.Model.UserModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class UserInfoModel implements Component{
    public UserModel userModel;
    @Override
    public String render() {
        File in = new File("src/main/resources/templates/User.html");
        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            doc.getElementById("username").text("Username: " + userModel.username);
            doc.getElementById("email").text("Email: " + userModel.email);
            doc.getElementById("birthDate").text("Birth Date: " + userModel.birthDate);
            doc.getElementById("address").text(userModel.address);
            doc.getElementById("credit").text("Credit: " + userModel.credit);

            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Error!</h3>";
        }
    }
}
