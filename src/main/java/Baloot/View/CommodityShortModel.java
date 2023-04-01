package Baloot.View;

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

    private String generateAddToBuyListScript(String commodityId) {
        return String.format("<script> commodity_id = %s;\n" +
                "function add_to_buy_list() {\n" +
                "        let url = document.getElementById(\"buy_list\").action;\n" +
                "        url += \"/\" + document.getElementsByName(\"user_id\")[0].value;\n" +
                "        url += \"/\" + commodity_id;\n" +
                "        document.getElementById(\"buy_list\").action = url;\n" +
                "\t}\n" +
                "\t</script>", commodityId);
    }

    private String generateLikeScript() {
        return "<script> function vote_like() {\n" +
                "        let url = document.getElementById(\"like_id\").action;\n" +
                "        const url_array = url.split(\"/\")\n" +
                "        const vote = url_array.pop()\n" +
                "        const commentId = url_array.pop()\n" +
                "        url = url_array.join(\"/\")\n" +
                "        url += \"/\" + document.getElementsByName(\"user_id\")[0].value;\n" +
                "        url += \"/\" + commentId;\n" +
                "        url += \"/\" + vote;\n" +
                "        document.getElementById(\"like_id\").action = url;\n" +
                "\t}\n" +
                "\t</script>";
    }

    private String generateDislikeScript() {
        return "<script> function vote_dislike() {\n" +
                "        let url = document.getElementById(\"dislike_id\").action;\n" +
                "        const url_array = url.split(\"/\")\n" +
                "        const vote = url_array.pop()\n" +
                "        const commentId = url_array.pop()\n" +
                "        url = url_array.join(\"/\")\n" +
                "        url += \"/\" + document.getElementsByName(\"user_id\")[0].value;\n" +
                "        url += \"/\" + commentId;\n" +
                "        url += \"/\" + vote;\n" +
                "        document.getElementById(\"dislike_id\").action = url;\n" +
                "\t}\n" +
                "\t</script>";
    }

    private String generateLikeVoteForm(Integer count, Integer commentId) {
        return String.format("<form id=\"like_id\" action=\"/voteComment/%d/1\" method=\"POST\" onsubmit=\"vote_like()\">\n" +
                "  <label for=\"\">%d</label>\n" +
                "  <input\n" +
                "    id=\"form_comment_id\"\n" +
                "    type=\"hidden\"\n" +
                "    name=\"comment_id\"\n" +
                "    value=\"01\"\n" +
                "  />\n" +
                "  <button type=\"submit\">like</button>\n" +
                "</form>", commentId, count);
    }

    private String generateDislikeVoteForm(Integer count, Integer commentId) {
        return String.format("<form id=\"dislike_id\" action=\"/voteComment/%d/-1\" method=\"POST\" onsubmit=\"vote_dislike()\">\n" +
                "  <label for=\"\">%d</label>\n" +
                "  <input\n" +
                "    id=\"form_comment_id\"\n" +
                "    type=\"hidden\"\n" +
                "    name=\"comment_id\"\n" +
                "    value=\"01\"\n" +
                "  />\n" +
                "  <button type=\"submit\">dislike</button>\n" +
                "</form>", commentId, count);
    }

    private String generateHtmlTableRow(CommentReportModel commentReportModel) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String row =
                "<td>" + commentReportModel.username + "</td>\n" +
                        "<td>" + commentReportModel.text + "</td>\n" +
                        "<td>" + formatter.format(commentReportModel.date) + "</td>\n" +
                        "<td>" + generateLikeVoteForm(commentReportModel.like, commentReportModel.id) + "</td>\n" +
                        "<td>" + generateDislikeVoteForm(commentReportModel.dislike, commentReportModel.id) + "</td>\n";
        return row;
    }

    @Override
    public String render() {
        File in = new File("src/main/resources/templates/Commodity.jsp");
        try {
            Document doc = Jsoup.parse(in, "UTF-8");
            doc.append(generateRateScript(commodityModel.id.toString()));
            doc.append(generateAddToBuyListScript(commodityModel.id.toString()));
            doc.append(generateLikeScript());
            doc.append(generateDislikeScript());

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

            Element addToBuyListForm = doc.getElementsByTag("form").get(1);
            addToBuyListForm.attr("onsubmit", "add_to_buy_list()");
            addToBuyListForm.attr("id", "buy_list");
            addToBuyListForm.attr("action", "/addToBuyList");

            return doc.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Error!</h3>";
        }
    }
}
