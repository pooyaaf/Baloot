package Baloot.Model.view;

import Baloot.Model.CommentReportModel;
import Baloot.Model.CommodityModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommodityShortModel implements Component {
    public CommodityModel commodityModel;
    public ArrayList<CommentReportModel> commentsList;

    private String generateRateScript(String commodityId) {
        return String.format("<script> let commodity_id = %s;\n" +
                "function rate() {\n" +
                "        let y = document.getElementById(\"myRate\").action;\n" +
                "        y += \"/\" + document.getElementsByName(\"user_id\")[0].value;\n" +
                "        y += \"/\" + commodity_id;\n" +
                "        y += \"/\" + document.getElementsByName(\"quantity\")[0].value;\n" +
                "        document.getElementById(\"myRate\").action = y;\n" +
                "\t}\n" +
                "\t</script>", commodityId);
    }

    private String generateHtmlTableRow(CommentReportModel commentReportModel) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String row =
                "<td>" + commentReportModel.userEmail + "</td>\n" +
                        "<td>" + commentReportModel.text + "</td>\n" +
                        "<td>" + formatter.format(commentReportModel.date) + "</td>\n" +
                        "<td>" + commentReportModel.like + "</td>\n" +
                        "<td>" + commentReportModel.dislike + "</td>\n";
        return row;
    }
    @Override
    public String render() {
        File in = new File("src/main/resources/templates/Commodity.html");
        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            doc.append(generateRateScript(commodityModel.id.toString()));
            doc.getElementById("id").text("Id: " + commodityModel.id.toString());
            doc.getElementById("name").text("Name: " + commodityModel.name);
            doc.getElementById("providerId").text("Provider Id: " + commodityModel.providerId.toString());
            doc.getElementById("price").text("Price: " + String.valueOf(commodityModel.price));
            doc.getElementById("categories").text("Categories: " + String.join(", ", commodityModel.categories));
            doc.getElementById("rating").text("Rating: " + commodityModel.rating);
            doc.getElementById("inStock").text("In Stock: " + commodityModel.inStock.toString());
            Element table = doc.getElementsByTag("table").first();
            table.getElementsByTag("tr").last().remove();
            for (CommentReportModel commentReportModel : commentsList) {
                String row = generateHtmlTableRow(commentReportModel);
                table.append(row);
            }

            Element rateForm = doc.getElementsByTag("form").first();
            rateForm.attr("onsubmit", "rate()");
            rateForm.attr("id", "myRate");
            rateForm.attr("action", "/rateCommodity");

            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Error!</h3>";
        }
    }
}
