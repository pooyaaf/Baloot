package Baloot.Entity;


import Baloot.Exception.CommodityNotInStuck;
import Baloot.Model.CommentReportModel;
import Baloot.Model.CommodityModel;
import Baloot.View.CommodityShortModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Integer, Comment> comments;

    public Commodity(CommodityModel model) {
        super();
        id = model.id;
        name = model.name;
        providerId = model.providerId;
        price = model.price;
        categories = model.categories;
        rating = model.rating;
        inStock = model.inStock;
        comments = new HashMap<>();
    }

    public CommodityModel getModel() {
        CommodityModel model = new CommodityModel();
        model.id = id;
        model.name = name;
        model.providerId = providerId;
        model.price = price;
        model.categories = categories;
        model.rating = rating;
        model.inStock = inStock;
        return model;
    }

    public CommodityShortModel getReportModel() {
        CommodityShortModel model = new CommodityShortModel();
        model.commentsList = new ArrayList<>();
        model.commodityModel = new CommodityModel();
        model.commodityModel.id = id;
        model.commodityModel.name = name;
        model.commodityModel.providerId = providerId;
        model.commodityModel.price = price;
        model.commodityModel.categories = categories;
        model.commodityModel.rating = rating;
        model.commodityModel.inStock = inStock;
        model.commentsList = getCommentsList();
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

    public void increaseInStuck() {
        inStock += 1;
    }

    public void decreaseInStuck() throws CommodityNotInStuck {
        if (inStock <= 0)
            throw new CommodityNotInStuck();
        inStock -= 1;
    }

    public Double getRate() {
        return rating;
    }

    public Boolean isInCategory(String targetCategory) {
        for (String category : categories) {
            if (category.equals(targetCategory)) {
                return true;
            }
        }
        return false;
    }

    public void putComment(Comment comment) {
        comments.put(comment.getId(), comment);
    }

    public ArrayList<CommentReportModel> getCommentsList() {
        ArrayList<CommentReportModel> result = new ArrayList<>();
        for (Comment comment : comments.values()) {
            result.add(comment.getReportModel());
        }
        return result;
    }

    public boolean checkPriceRange(double start_price, double end_price) {
        return start_price <= price && price <= end_price;
    }
}
