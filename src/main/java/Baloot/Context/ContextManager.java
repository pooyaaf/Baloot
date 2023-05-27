package Baloot.Context;

import Baloot.Entity.*;
import Baloot.Exception.*;
import Baloot.Model.*;
import Baloot.Repository.*;
import Baloot.Validation.IgnoreFailureTypeAdapterFactory;
import Baloot.View.CommodityListModel;
import Baloot.View.ProviderViewModel;
import Baloot.View.UserInfoModel;
import Baloot.service.Http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.lang.Math.max;

@Component
public class ContextManager {
    private static final BasicDataSource ds = new BasicDataSource();
    private final static String dbURL = "jdbc:mysql://localhost:3306/balootdb?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&allowMultiQueries=true";
    private final static String dbUserName = "root";
    private final static String dbPassword = "alibatman";
    public static CommodityRepository commodityRepository;
    public static  ProviderRepository providerRepository;
    public static UserRepository userRepository;

    public static DiscountRepository discountRepository;

    @Autowired
    public ContextManager(ProviderRepository providerRepository,UserRepository userRepository,CommodityRepository commodityRepository, DiscountRepository discountRepository) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
        this.commodityRepository=commodityRepository;
        this.discountRepository = discountRepository;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        ds.setUsername(dbUserName);
        ds.setPassword(dbPassword);
        ds.setUrl(dbURL);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
        setEncoding();
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    public static void setEncoding() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute("ALTER DATABASE balootdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
            connection.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private static HashMap<String, Category> categories = new HashMap<>();
    private static HashMap<String, User> users = new HashMap<>();
    private static HashMap<Integer, Provider> providers = new HashMap<>();
    private static HashMap<Integer, Commodity> commodities = new HashMap<>();
    private static HashMap<Integer, Comment> comments = new HashMap<>();
    private static HashMap<String, Discount> discounts = new HashMap<>();
    private static ContextManager instance;

    public static ContextManager getInstance() {
        if (instance == null) {
            instance = new ContextManager(providerRepository,userRepository,commodityRepository,discountRepository);
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

        String providers = Http.Get("v2/providers");
        ProviderModel[] providersArray = gson.fromJson(providers, ProviderModel[].class);
        for (ProviderModel model : providersArray) {
            Provider provider = new Provider(model);
            putProvider(model.id, provider);
        }

        String commodities = Http.Get("v2/commodities");
        CommodityModel[] commodityArray = gson.fromJson(commodities, CommodityModel[].class);
        for (CommodityModel model : commodityArray) {
            Commodity commodity = new Commodity(model);
            putCommodity(model.id, commodity);
        }

        String discountsString = Http.Get("discount");
        DiscountModel[] discountArray = gson.fromJson(discountsString, DiscountModel[].class);
        for (DiscountModel model : discountArray) {
            Discount discount = new Discount(model);
            putDiscount(discount.getDiscountCode(), discount);
        }
    }


    public static void resetContext() {
        categories.clear();
        comments.clear();
        commodities.clear();
        users.clear();
        providers.clear();
    }

    @SneakyThrows
    public void putUser(String username, User user) {
        userRepository.save(user);
    }


    public User getUser(String username) throws Exception, UserNotFound {
        Optional<User> userOptional =  userRepository.findById(username);
        if (userOptional.isEmpty()) throw new UserNotFound();
        return userOptional.get();
    }

    public Category getCategory(String category) throws CategoryNotFound {
        if (!categories.containsKey(category)) {
            throw new CategoryNotFound();
        }
        return categories.get(category);
    }

    @SneakyThrows
    public void putProvider(Integer id, Provider provider) {
        providerRepository.save(provider);
    }

    public Provider getProvider(Integer id) throws Exception, ProviderNotFound {
//        Connection con = getConnection();
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("SELECT `id`,`image`,`name`,`registrydate` FROM `balootdb`.`provider` \n");
//        builder.append(String.format("WHERE id=%d", id));
//
//        Statement stmt = con.createStatement();
//        ResultSet result = stmt.executeQuery(builder.toString());
//
//        if (!result.next()) {
//            con.close();
//            stmt.close();
//            throw new ProviderNotFound();
//        }
//        ProviderModel model = new ProviderModel();
//        model.id = result.getInt("id");
//        model.name = result.getString("name");
//        model.image = result.getString("image");
//        model.registryDate = result.getString("registrydate");
//
//
//        con.close();
//        stmt.close();
        Optional<Provider> providerOptional = providerRepository.findById(id);
        if (providerOptional.isEmpty()) throw new ProviderNotFound();
        return providerOptional.get();
    }

    public Iterable<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public void updateCategories(Commodity commodity) {
        List<String> categoriesName = commodity.getCategories();
        for (String categoryName : categoriesName) {
            if (!categories.containsKey(categoryName)) {
                categories.put(categoryName, new Category(categoryName));
            }
            Category category = categories.get(categoryName);
            category.addCommodity(commodity);
        }
    }


    @SneakyThrows
    public void updateProvider(Commodity commodity) {
        Integer providerId = commodity.getProviderId();
        getProvider(providerId).addCommodity(commodity);
    }

    public void  putCommodity(Integer id, Commodity commodity) {
        commodityRepository.save(commodity);
        updateProvider(commodity);
    }

    public Commodity getCommodity(Integer id) throws Exception, CommodityNotFound {
        Optional<Commodity> commodityOptional = commodityRepository.findById(id);
        if (commodityOptional.isEmpty()) throw new CommodityNotFound();
        return commodityOptional.get();
    }

    public Iterable<Commodity> getAllCommodities() {
        return commodityRepository.findAll();
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

//    @SneakyThrows
//    public void updateCommodity(Comment comment) {
//        Optional<Commodity> commodityOptional = commodityRepository.findById(comment.getCommodityId());
//        if (commodityOptional.isEmpty()) throw new CommodityNotFound();
//        commodityOptional.get().putComment(comment);
//    }

    public boolean isUserPassExist(String username, String password) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isEmpty()) return false;
        if (!optionalUser.get().getPassword().equals(password)) return false;
        return true;
    }

    public void putDiscount(String id, Discount discount) {
        discountRepository.save(discount);
    }

    public Discount getDiscount(String id) throws DiscountNotFound{
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isEmpty()) throw new DiscountNotFound();
        return optionalDiscount.get();
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
