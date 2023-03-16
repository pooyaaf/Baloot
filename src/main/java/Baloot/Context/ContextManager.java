package Baloot.Context;

import Baloot.Entity.*;
import Baloot.Exception.*;
import Baloot.Model.CommentModel;
import Baloot.Model.ProviderModel;
import Baloot.Model.UserModel;
import Baloot.Model.CommodityModel;
import Baloot.Validation.IgnoreFailureTypeAdapterFactory;
import Baloot.service.Http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ContextManager {
    private static HashMap<String, Category> categories = new HashMap<>();
    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<Integer, Provider> providers = new HashMap<>();
    private static HashMap<Integer, Commodity> commodities = new HashMap<>();
    private static HashMap<Integer, Comment> comments = new HashMap<>();

    public static void initialize() {
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

        String commentsString = Http.Get("comments");
        CommentModel[] commentsArray = gson.fromJson(commentsString, CommentModel[].class);
        for (CommentModel model : commentsArray) {
            Comment comment = new Comment(model);
            putComment(comment.getId(), comment);
        }
    }


    public static void resetContext() {
        categories.clear();
        commodities.clear();
        users.clear();
        providers.clear();
    }

    public static void putUser(String username, User user) {
        users.put(username, user);
    }

    public static User getUser(String username) throws Exception, UserNotFound {
        if (!users.containsKey(username)) {
            throw new UserNotFound();
        }
        return users.get(username);
    }

    public static Category getCategory(String category) throws CategoryNotFound {
        if (!categories.containsKey(category)) {
            throw new CategoryNotFound();
        }
        return categories.get(category);
    }

    public static void putProvider(Integer id, Provider provider) {
        providers.put(id, provider);
    }

    public static Provider getProvider(Integer id) throws Exception, ProviderNotFound {
        if (!providers.containsKey(id)) {
            throw new ProviderNotFound();
        }
        return providers.get(id);
    }

    public static void updateCategories(Commodity commodity) {
        String[] categoriesName = commodity.getCategories();
        for (String categoryName : categoriesName) {
            if (!categories.containsKey(categoryName)) {
                categories.put(categoryName, new Category(categoryName));
            }
            Category category = categories.get(categoryName);
            category.addCommodity(commodity);
        }
    }

    public static void updateProvider(Commodity commodity) {
        Integer providerId = commodity.getProviderId();
        if (providers.containsKey(providerId)) {
            Provider provider = providers.get(providerId);
            provider.addCommodity(commodity);
        }
    }

    public static void putCommodity(Integer id, Commodity commodity) {
        commodities.put(id, commodity);
        updateCategories(commodity);
        updateProvider(commodity);
    }

    public static Commodity getCommodity(Integer id) throws Exception, CommodityNotFound {
        if (!commodities.containsKey(id)) {
            throw new CommodityNotFound();
        }
        return commodities.get(id);
    }

    public static Collection<Commodity> getAllCommodities() {
        return commodities.values();
    }

    public static Collection<Commodity> getCommodityByCategory(String category) {
        ArrayList<Commodity> result = new ArrayList<>();

        for (Commodity commodity : commodities.values()) {
            if (commodity.isInCategory(category)) {
                result.add(commodity);
            }
        }

        return result;
    }

    public static void updateCommodity(Comment comment) {
        Integer commodityId = comment.getCommodityId();
        if (commodities.containsKey(commodityId)) {
            Commodity commodity = commodities.get(commodityId);
            commodity.putComment(comment);
        }
    }

    public static void putComment(Integer id, Comment comment) {
        comments.put(id, comment);
        updateCommodity(comment);
    }

    public static Comment getComment(Integer id) throws CommentNotFound {
        if (!comments.containsKey(id)) {
            throw new CommentNotFound();
        }
        return comments.get(id);
    }
}
