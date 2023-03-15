package Baloot.Model.view;

import Baloot.Model.CommodityModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CommodityListModel implements Component{
    public ArrayList<CommodityModel> commoditiesList;
    private String generateHtmlTableRow(CommodityModel commodityModel) {
        String row =
                "<td>" + commodityModel.id.toString() + "</td>\n" +
                "<td>" + commodityModel.name + "</td>\n" +
                "<td>" + commodityModel.providerId.toString() + "</td>\n" +
                "<td>" + String.valueOf(commodityModel.price) + "</td>\n" +
                "<td>" + String.join(", ", commodityModel.categories) + "</td>\n" +
                "<td>" + String.valueOf(commodityModel.rating) + "</td>\n" +
                "<td>" + commodityModel.inStock.toString() + "</td>\n" +
                "<td><a href=\"/commodities/" + commodityModel.id.toString() + "\">Link</a></td>";
        return row;
    }
    public String render() {
        File in = new File("src/main/resources/templates/Commodities.html");
        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            Element table = doc.getElementsByTag("table").first();
            table.getElementsByTag("tr").last().remove();
            for (CommodityModel commodityModel : commoditiesList) {
                String row = generateHtmlTableRow(commodityModel);
                table.append(row);
            }
            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Error!</h3>";
        }
    }
}
