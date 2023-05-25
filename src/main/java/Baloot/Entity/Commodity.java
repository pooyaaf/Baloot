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
    private int providerId;
    @Getter
    @Setter
    @Column(name = "price")
    private double price;
    @ElementCollection
    @CollectionTable(name = "CommodityCategories", joinColumns = @JoinColumn(name = "CommodityId"))
    @Column(name = "Name")
    private List<String> categories;
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


    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "commodityId")
    private Map<Integer, Comment> comments;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "commodityId")
    private Set<Rate> rates;

//    public void setCategories(String[] array) {
//        this.categories = String.join(",", array);
//    }
//
//    public String[] getCategories() {
//        return categories.split(",");
//    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
    public Commodity(CommodityModel model) {
        super();
        id = model.id;
        name = model.name;
        providerId = model.providerId;
        price = model.price;
        categories = new ArrayList<>(Arrays.asList(model.categories));
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
        model.categories = categories.toArray(new String[0]);
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
        model.commodityModel.categories = categories.toArray(new String[0]);
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

    public boolean isInSimilarCategory(List<String>  categories) {
        for (String category : categories) {
            for (String secondCategory : this.categories) {
                if (category.equals(secondCategory)) return true;
            }
        }
        return false;
    }
}
