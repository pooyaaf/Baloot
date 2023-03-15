package Baloot.Model.view;

import Baloot.Model.CommodityModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class CommodityShortModel implements Component {
    public CommodityModel commodityModel;

    @Override
    public String render() {
        File in = new File("src/main/resources/templates/Commodity.html");
        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            doc.getElementById("id").text("Id: " + commodityModel.id.toString());
            doc.getElementById("name").text("Name: " + commodityModel.name);
            doc.getElementById("providerId").text("Provider Id: " + commodityModel.providerId.toString());
            doc.getElementById("price").text("Price: " + String.valueOf(commodityModel.price));
            doc.getElementById("categories").text("Categories: " + String.join(", ", commodityModel.categories));
            doc.getElementById("rating").text("Rating: " + commodityModel.rating);
            doc.getElementById("inStock").text("In Stock: " + commodityModel.inStock.toString());

            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Error!</h3>";
        }
    }
}
