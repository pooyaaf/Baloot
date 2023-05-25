package Baloot.Context;

import Baloot.Entity.*;
import Baloot.Exception.*;
import Baloot.Model.*;
import Baloot.Repository.CommodityRepository;
import Baloot.Repository.ProviderRepository;
import Baloot.Repository.UserRepository;
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
    private final static String dbPassword = "toor";
    public static CommodityRepository commodityRepository;
    public static  ProviderRepository providerRepository;
    public static UserRepository userRepository;
    @Autowired
    public ContextManager(ProviderRepository providerRepository,UserRepository userRepository,CommodityRepository commodityRepository) {
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
        // TODO : Below should be uncommited when other puts be ok
//        this.commodityRepository=commodityRepository;
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
            statement.execute("ALTER DATABASE IEMDB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
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
            instance = new ContextManager(providerRepository,userRepository,commodityRepository);
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
//        Connection con = getConnection();
//        UserInfoModel model = user.getUserInfoModel();
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("INSERT INTO `balootdb`.`user` (");
//        builder.append("`username`, `activediscountcode`, `address`, `birthdate`, `credit`, `email`, `password`)");
//        builder.append(" VALUES ");
//        builder.append(String.format("('%s', '%s', '%s', '%s', %d, '%s', '%s')",
//                model.userModel.username,
//                model.userModel.activediscountcode,
//                model.userModel.address,
//                model.userModel.birthDate,
//                model.userModel.credit,
//                model.userModel.email,
//                model.userModel.password
//        ));
//        Statement stmt = con.createStatement();
//        try {
//            stmt.execute(builder.toString());
//        } catch (Exception e) {
//            builder = new StringBuilder();
//            builder.append("UPDATE `balootdb`.`user` ");
//            builder.append(String.format("SET `activediscountcode` = '%s', `address` = '%s', `birthdate` = '%s', `credit` = %d, `email` = '%s', `password` = '%s' ",
//                    model.userModel.activediscountcode,
//                    model.userModel.address,
//                    model.userModel.birthDate,
//                    model.userModel.credit,
//                    model.userModel.email,
//                    model.userModel.password));
//            builder.append(String.format("WHERE `username` = '%s'", model.userModel.username));
//            stmt.execute(builder.toString());
//        } finally {
//            con.close();
//            stmt.close();
//        }
        userRepository.save(user);
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

    @SneakyThrows
    public void putProvider(Integer id, Provider provider) {
//        providers.put(id, provider);
//        Connection con = getConnection();
//        ProviderViewModel model = provider.GetProviderViewModel();
//        StringBuilder builder = new StringBuilder();
//
//        builder.append("INSERT INTO `balootdb`.`provider`( \n");
//        builder.append("`id`,`image`, `name`,`registrydate`)");
//        builder.append("VALUES");
//        builder.append(String.format("(%d, \"%s\", \"%s\", \"%s\")",
//                model.providerModel.id,
//                model.providerModel.image,
//                model.providerModel.name,
//                model.providerModel.registryDate
//                ));
//        builder.append("ON DUPLICATE KEY UPDATE ");
//        builder.append(
//                "`id`=VALUES(`id`), `image`=VALUES(`image`), `name`=VALUES(`name`), `registrydate`=VALUES(`registrydate`);\n");
//
//        System.err.println(builder.toString());
//        Statement stmt = con.createStatement();
//        stmt.execute(builder.toString());
//
//        con.close();
//        stmt.close();
        providerRepository.save(provider);
    }

    public Provider getProvider(Integer id) throws Exception, ProviderNotFound {
        Connection con = getConnection();
        StringBuilder builder = new StringBuilder();

        builder.append("SELECT `id`,`image`,`name`,`registrydate` FROM `balootdb`.`provider` \n");
        builder.append(String.format("WHERE id=%d", id));

        Statement stmt = con.createStatement();
        ResultSet result = stmt.executeQuery(builder.toString());

        if (!result.next()) {
            con.close();
            stmt.close();
            throw new ProviderNotFound();
        }
        ProviderModel model = new ProviderModel();
        model.id = result.getInt("id");
        model.name = result.getString("name");
        model.image = result.getString("image");
        model.registryDate = result.getString("registrydate");


        con.close();
        stmt.close();

        return new Provider(model);
    }

    public Collection<Provider> getAllProviders() {
        return providers.values();
    }

    public void updateCategories(Commodity commodity) {
        List<String> categoriesName = List.of(commodity.getCategories());
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

    public void  putCommodity(Integer id, Commodity commodity) {
        // TODO : Below should be uncommited when other puts be ok
//        commodityRepository.save(commodity);
        commodities.put(id, commodity);
        updateCategories(commodity);
        updateProvider(commodity);
    }

    public Commodity getCommodity(Integer id) throws Exception, CommodityNotFound {
        if (!commodities.containsKey(id)) {
            throw new CommodityNotFound();
        }
          return commodityRepository.findById(id).get();
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
                if (commodity.isInSimilarCategory(List.of(targetCommodity.getCategories()))) score += 11;
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
