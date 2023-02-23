package Baloot.Entity;


import Baloot.Model.CommodityModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Commodity {
    @Getter
    @Setter
   private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int providerId;
    @Getter
    @Setter
    private double price;
    @Getter
    @Setter
    private String[] categories;
    @Getter
    @Setter
    private double rating;
    @Getter
    @Setter
    private int inStock;

    public Commodity(CommodityModel model) {
        super();
        id = model.id;
        name = model.name;
        providerId = model.providerId;
        price = model.price;
        categories = model.categories;
        rating = model.rating;
        inStock = model.inStock;
    }
}
