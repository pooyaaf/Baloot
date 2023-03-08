package Baloot.Model;

import Baloot.Model.view.Component;

public class CommodityShortModel implements Component {
    public int id;
    public String name;
    public int providerId;
    public double price;
    public String[] categories;
    public double rating;
    @Override
    public String render() {
        return
                "<tr>" +
                        "<td>" + name + "</td>" +
                        "<td>" + providerId + "</td>" +
                        "<td>" + rating + "</td>" +
                        "</tr>";
    }
}
