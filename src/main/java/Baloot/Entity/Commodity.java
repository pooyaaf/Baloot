package Baloot.Entity;


import Baloot.Exception.CommodityNotInStuck;
import Baloot.Model.CommentReportModel;
import Baloot.Model.CommodityModel;
import Baloot.View.CommodityShortModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "commodity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commodity {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Getter
    @Setter
    @Column(name = "name")

    private String name;
    @Getter
    @Setter
    @Column(name = "provider_id")
    private int providerId;
    @Getter
    @Setter
    @Column(name = "price")
    private double price;
    private String categories;
    @Getter
    @Setter
    @Column(name = "rating")
    private double rating;
    @Getter
    @Setter
    @Column(name = "inStock")
    private int inStock;
    @Getter
    @Setter
    @Column(name = "image")
    private String image;


    @OneToMany(mappedBy = "commodityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<Integer, Comment> comments;

    @OneToMany(mappedBy = "commodityId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rate> rates;

    public void setCategories(String[] array) {
        this.categories = String.join(",", array);
    }

    public String[] getCategories() {
        return categories.split(",");
    }

    public Commodity(CommodityModel model) {
        super();
        id = model.id;
        name = model.name;
        providerId = model.providerId;
        price = model.price;
        this.setCategories(model.categories);
        rating = model.rating;
        inStock = model.inStock;
        image = model.image;
        comments = new HashMap<>();
        rates = new HashSet<>();
    }

    public CommodityModel getModel() {
        CommodityModel model = new CommodityModel();
        model.id = id;
        model.name = name;
        model.providerId = providerId;
        model.price = price;
        model.categories = this.getCategories();
        model.rating = rating;
        model.inStock = inStock;
        model.image = image;
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
        model.commodityModel.categories = this.getCategories();
        model.commodityModel.rating = rating;
        model.commodityModel.inStock = inStock;
        model.commodityModel.image = image;
        model.commentsList = getCommentsList();
        return model;
    }

    public void addRate(String username, Integer rate) {
        rates.add(new Rate(this.id, username, rate));
        Double mean = 0.0;
        for (Rate val : rates) {
            mean += val.rateNumber;
        }
        rating = mean / rates.size();
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
        // TODO

//        for (String category : categories) {
//            if (category.equals(targetCategory)) {
//                return true;
//            }
//        }
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

    public boolean isInSimilarCategory(String[] categories) {
        // TODO

//        for (String category : categories) {
//            for (String secondCategory : this.categories) {
//                if (category.equals(secondCategory)) return true;
//            }
//        }
        return false;
    }
}
