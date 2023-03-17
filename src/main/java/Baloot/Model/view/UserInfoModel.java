package Baloot.Model.view;

import Baloot.Model.CommodityModel;
import Baloot.Model.UserModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserInfoModel implements Component {
    public UserModel userModel;
    public ArrayList<CommodityModel> buyList;
    public ArrayList<CommodityModel> purchasedList;

    private String generateHtmlTableRow(CommodityModel commodityModel) {
        String row = "<td>" + commodityModel.id.toString() + "</td>\n" +
                "<td>" + commodityModel.name + "</td>\n" +
                "<td>" + commodityModel.providerId.toString() + "</td>\n" +
                "<td>" + String.valueOf(commodityModel.price) + "</td>\n" +
                "<td>" + String.join(", ", commodityModel.categories) + "</td>\n" +
                "<td>" + String.valueOf(commodityModel.rating) + "</td>\n" +
                "<td>" + commodityModel.inStock.toString() + "</td>\n" +
                "<td><a href=\"/commodities/" + commodityModel.id.toString() + "\">Link</a></td>";
        return row;
    }

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

            Element firstTable = doc.getElementsByTag("table").first();
            firstTable.getElementsByTag("tr").last().remove();
            for (CommodityModel commodityModel : buyList) {
                String row = generateHtmlTableRow(commodityModel);
                firstTable.append(row);
            }

            Element secondTable = doc.getElementsByTag("table").last();
            secondTable.getElementsByTag("tr").last().remove();
            for (CommodityModel commodityModel : purchasedList) {
                String row = generateHtmlTableRow(commodityModel);
                secondTable.append(row);
            }
            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Error!</h3>";
        }
    }
}
