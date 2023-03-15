package Baloot.Model.view;

import Baloot.Model.CommodityModel;
import Baloot.Model.ProviderModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProviderViewModel implements Component{
    public ProviderModel providerModel;
    public ArrayList<CommodityModel> commoditiesList;
    private String generateHtmlTableRow(CommodityModel commodityModel) {
        String row = "<td>" + commodityModel.id.toString() + "</td>\n" +
                        "<td>" + commodityModel.name + "</td>\n" +
                        "<td>" + String.valueOf(commodityModel.price) + "</td>\n" +
                        "<td>" + String.join(", ", commodityModel.categories) + "</td>\n" +
                        "<td>" + String.valueOf(commodityModel.rating) + "</td>\n" +
                        "<td>" + commodityModel.inStock.toString() + "</td>\n" +
                        "<td><a href=\"/commodities/" + commodityModel.id.toString() + "\">Link</a></td>";
        return row;
    }
    @Override
    public String render() {
        File in = new File("src/main/resources/templates/Provider.html");
        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            Element table = doc.getElementsByTag("table").first();
            table.getElementsByTag("tr").last().remove();
            doc.getElementById("id").text("Id: " + providerModel.id);
            doc.getElementById("name").text("Name: " + providerModel.name);
            doc.getElementById("registryDate").text("Registry Date: " + providerModel.registryDate);
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
