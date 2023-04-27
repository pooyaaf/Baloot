package Baloot.Context;

import Baloot.Entity.*;
import Baloot.Exception.*;
import Baloot.Model.*;
import Baloot.Validation.IgnoreFailureTypeAdapterFactory;
import Baloot.View.CommodityListModel;
import Baloot.service.Http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Math.max;

public class ContextManager {
    private static HashMap<String, Category> categories = new HashMap<>();
    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<Integer, Provider> providers = new HashMap<>();
    private static HashMap<Integer, Commodity> commodities = new HashMap<>();
    private static HashMap<Integer, Comment> comments = new HashMap<>();
    private static HashMap<String, Discount> discounts = new HashMap<>();
    private static ContextManager instance;

    public static ContextManager getInstance() {
        if (instance == null) {
            instance = new ContextManager();
            instance.initialize();
        }
        return instance;
    }

    private User findUserByEmail(String email) {
        for (User user : users.values()) {
            if (user.getEmail() == email) {
                return user;
            }
        }
        return null;
    }

    public void initialize() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapterFactory(new IgnoreFailureTypeAdapterFactory())
                .create();

        String users = Http.Get("users");
        UserModel[] userArray = gson.fromJson(users, UserModel[].class);
        for (UserModel model : userArray) {
            User user = new User(model);
            putUser(model.username, user);
        }

        String providers = Http.Get("providers");
        ProviderModel[] providersArray = gson.fromJson(providers, ProviderModel[].class);
        for (ProviderModel model : providersArray) {
            Provider provider = new Provider(model);
            putProvider(model.id, provider);
        }

        String commodities = Http.Get("commodities");
        CommodityModel[] commodityArray = gson.fromJson(commodities, CommodityModel[].class);
        for (CommodityModel model : commodityArray) {
            Commodity commodity = new Commodity(model);
            putCommodity(model.id, commodity);
        }

//        String commentsString = Http.Get("comments");
//        CommentInputModel[] commentsArray = gson.fromJson(commentsString, CommentInputModel[].class);
//        for (CommentInputModel model : commentsArray) {
//            User user = findUserByEmail(model.userEmail);
//            if (user == null) continue;
//            CommentModel commentModel = new CommentModel();
//            commentModel.commodityId = model.commodityId;
//            commentModel.text = model.text;
//            commentModel.user = user;
//            commentModel.date = model.date;
//            Comment comment = new Comment(commentModel);
//            putComment(comment.getId(), comment);
//        }
//
//        String discountsString = Http.Get("discount");
//        DiscountModel[] discountArray = gson.fromJson(discountsString, DiscountModel[].class);
//        for (DiscountModel model : discountArray) {
//            Discount discount = new Discount(model);
//            putDiscount(discount.getDiscountCode(), discount);
//        }
    }


    public static void resetContext() {
        categories.clear();
        comments.clear();
        commodities.clear();
        users.clear();
        providers.clear();
    }

    public void putUser(String username, User user) {
        users.put(username, user);
    }

    public User getUser(String username) throws Exception, UserNotFound {
        if (!users.containsKey(username)) {
            throw new UserNotFound();
        }
        return users.get(username);
    }

    public Category getCategory(String category) throws CategoryNotFound {
        if (!categories.containsKey(category)) {
            throw new CategoryNotFound();
        }
        return categories.get(category);
    }

    public void putProvider(Integer id, Provider provider) {
        providers.put(id, provider);
    }

    public Provider getProvider(Integer id) throws Exception, ProviderNotFound {
        if (!providers.containsKey(id)) {
            throw new ProviderNotFound();
        }
        return providers.get(id);
    }

    public Collection<Provider> getAllProviders() {
        return providers.values();
    }

    public void updateCategories(Commodity commodity) {
        String[] categoriesName = commodity.getCategories();
        for (String categoryName : categoriesName) {
            if (!categories.containsKey(categoryName)) {
                categories.put(categoryName, new Category(categoryName));
            }
            Category category = categories.get(categoryName);
            category.addCommodity(commodity);
        }
    }

    public void updateProvider(Commodity commodity) {
        Integer providerId = commodity.getProviderId();
        if (providers.containsKey(providerId)) {
            Provider provider = providers.get(providerId);
            provider.addCommodity(commodity);
        }
    }

    public void putCommodity(Integer id, Commodity commodity) {
        commodities.put(id, commodity);
        updateCategories(commodity);
        updateProvider(commodity);
    }

    public Commodity getCommodity(Integer id) throws Exception, CommodityNotFound {
        if (!commodities.containsKey(id)) {
            throw new CommodityNotFound();
        }
        return commodities.get(id);
    }

    public Collection<Commodity> getAllCommodities() {
        return commodities.values();
    }

    public Collection<Commodity> getCommodityByCategory(String category) {
        ArrayList<Commodity> result = new ArrayList<>();

        for (Commodity commodity : commodities.values()) {
            if (commodity.isInCategory(category)) {
                result.add(commodity);
            }
        }

        return result;
    }

    public void updateCommodity(Comment comment) {
        Integer commodityId = comment.getCommodityId();
        if (commodities.containsKey(commodityId)) {
            Commodity commodity = commodities.get(commodityId);
            commodity.putComment(comment);
        }
    }

    public void putComment(Integer id, Comment comment) {
        comments.put(id, comment);
        updateCommodity(comment);
    }

    public Comment getComment(Integer id) throws CommentNotFound {
        if (!comments.containsKey(id)) {
            throw new CommentNotFound();
        }
        return comments.get(id);
    }

    public boolean isUserPassExist(String username, String password) {
        if (!users.containsKey(username)) {
            return false;
        }
        if (!users.get(username).getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    public void putDiscount(String id, Discount discount) {
        discounts.put(id, discount);
    }

    public Discount getDiscount(String id) throws DiscountNotFound{
        if (!discounts.containsKey(id)) throw new DiscountNotFound();
        return discounts.get(id);
    }

    public ArrayList<Commodity> getSuggestedCommodities(Commodity targetCommodity) {
        HashMap<Integer, Double> scores = new HashMap<>();
        for (Commodity commodity : commodities.values()) {
            if (commodity.getId() != targetCommodity.getId()) {
                double score = commodity.getRating();
                if (commodity.isInSimilarCategory(targetCommodity.getCategories())) score += 11;
                scores.put(commodity.getId(), score);
            }
        }
        ArrayList<Map.Entry<Integer, Double>> sortedList = new ArrayList<>(scores.entrySet());
        Collections.sort(sortedList, new Comparator<HashMap.Entry<Integer, Double>>() {
            public int compare(HashMap.Entry<Integer, Double> o1, HashMap.Entry<Integer, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        ArrayList<Commodity> sorted = new ArrayList<>();
        for (Map.Entry<Integer, Double> element : sortedList.subList(max(0, sortedList.size()-5), sortedList.size())) {
            sorted.add(commodities.get(element.getKey()));
        }
        return sorted;
    }
}
