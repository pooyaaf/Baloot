package Baloot.Model.view;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommodityListModel {
    public ArrayList<CommodityModel> commoditiesList;
    public String render() {
        File in = new File("src/main/resources/templates/commodities.html");
        try {
            Document doc = Jsoup.parse(in, null);

            Element table = doc.getElementById("movies-table");
//            for (CommodityModel commodity : commoditiesList) {
//                table.append(commodity.render());
//            }
            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            ;
        }
        return "<h3>Error!</h3>";
    }
}
