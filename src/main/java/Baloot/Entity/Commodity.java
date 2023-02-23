package Baloot.Entity;


import Baloot.Model.CommodityModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
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
    HashMap<String, Integer> userRates = new HashMap<>();

    public Commodity(CommodityModel model) {
        super();
        id = model.id;
        name = model.name;
        providerId = model.providerId;
        price = model.price;
        categories = convertStringCategoriesToCategoriesList(model.categories);
        rating = model.rating;
        inStock = model.inStock;
    }

    public String[] convertStringCategoriesToCategoriesList(String stringCategories) {
        stringCategories = stringCategories.substring(1, stringCategories.length() - 1);
        return stringCategories.split(", ");
    }

    public String convertCategoriesListToStringCategories(String[] categoriesList) {
        String stringCategories = "[";
        for (int i = 0; i < categoriesList.length; i++) {
            stringCategories += categoriesList[i];
            if (i != categoriesList.length - 1) {
                stringCategories += ", ";
            }
        }
        stringCategories += "]";
        return stringCategories;
    }

    public CommodityModel getModel() {
        CommodityModel model = new CommodityModel();
        model.id = id;
        model.name = name;
        model.providerId = providerId;
        model.price = price;
        model.categories = convertCategoriesListToStringCategories(categories);
        model.rating = rating;
        model.inStock = inStock;
        return model;
    }

    public void addRate(String username, Integer rate) {
        userRates.put(username, rate);
        Double mean = 0.0;
        for (Integer val : userRates.values()) {
            mean += val;
        }
        rating = mean / userRates.size();
    }
}
