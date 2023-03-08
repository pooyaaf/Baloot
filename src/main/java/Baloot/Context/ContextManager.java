package Baloot.Context;

import Baloot.Entity.Category;
import Baloot.Entity.Commodity;
import Baloot.Entity.Provider;
import Baloot.Entity.User;
import Baloot.Exception.CategoryNotFound;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.ProviderNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.ProviderModel;
import Baloot.Model.UserModel;
import Baloot.Model.view.CommodityModel;
import Baloot.Validation.IgnoreFailureTypeAdapterFactory;
import Baloot.service.Http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.HashMap;

public class ContextManager {
    private static HashMap<String, Category> categories = new HashMap<>();
    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<Integer, Provider> providers = new HashMap<>();
    private static HashMap<Integer, Commodity> commodities = new HashMap<>();




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

        String commodities = Http.Get("commodities");
        CommodityModel[] commodityArray = gson.fromJson(commodities, CommodityModel[].class);
        for (CommodityModel model : commodityArray) {
            Commodity commodity = new Commodity(model);
            putCommodity(model.id, commodity);
        }

        String providers = Http.Get("providers");
        ProviderModel[] providersArray = gson.fromJson(providers, ProviderModel[].class);
        for (ProviderModel model : providersArray) {
            Provider provider = new Provider(model);
            putProvider(model.id, provider);
        }
        //todo - comment model should be add

//        String comments = Http.Get("comments");
//        CommentModel[] commentsArray = gson.fromJson(comments, CommentModel[].class);
//        for (CommentModel model : commentsArray) {
//        }
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

    public static void putCommodity(Integer id, Commodity commodity) {
        commodities.put(id, commodity);
        updateCategories(commodity);
    }

    public static Commodity getCommodity(Integer id) throws Exception, CommodityNotFound {
        if (!commodities.containsKey(id)) {
            throw new CommodityNotFound();
        }
        return commodities.get(id);
    }

    //
    public static Collection<Commodity> getAllCommodities() {
        return commodities.values();
    }
}
