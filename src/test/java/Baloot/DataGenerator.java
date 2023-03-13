package Baloot;

import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.Provider;
import Baloot.Entity.User;
import Baloot.Model.view.CommodityModel;
import Baloot.Model.ProviderModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.UserModel;

class DataGenerator {
    static public CommodityShortModel generateReportCommodityModel(int id, String name, int providerId, double price, String[] categories, double rating, int inStock) {
        CommodityShortModel reportCommodityModel = new CommodityShortModel();
        reportCommodityModel.commodityModel = new CommodityModel();
        reportCommodityModel.commodityModel.id = id;
        reportCommodityModel.commodityModel.name = name;
        reportCommodityModel.commodityModel.providerId = providerId;
        reportCommodityModel.commodityModel.price = price;
        reportCommodityModel.commodityModel.categories = categories;
        reportCommodityModel.commodityModel.rating = rating;
        reportCommodityModel.commodityModel.inStock = inStock;
        return reportCommodityModel;
    }

    static void GenerateUser(String username, String password, String email, String birthDate, String address, int credit) {
        UserModel userModel = new UserModel();
        userModel.username = username;
        userModel.password = password;
        userModel.address = address;
        userModel.birthDate = birthDate;
        userModel.credit = credit;
        userModel.email = email;
        User user = new User(userModel);
        ContextManager.putUser(username, user);
    }

    static void GenerateProvider(int id, String name, String registryDate) {
        ProviderModel providerModel = new ProviderModel();
        providerModel.id = id;
        providerModel.name = name;
        providerModel.registryDate = registryDate;
        Provider provider = new Provider(providerModel);
        ContextManager.putProvider(id, provider);

    }

    static void GenerateCommodity(int id, String name, int providerId, double price, String[] categories, double rating, int inStock) {
        CommodityModel commodityModel = new CommodityModel();
        commodityModel.id=id;
        commodityModel.inStock=inStock;
        commodityModel.rating=rating;
        commodityModel.providerId=providerId;
        commodityModel.categories=categories;
        commodityModel.name=name;
        commodityModel.price=price;
        Commodity commodity = new Commodity(commodityModel);
        ContextManager.putCommodity(id,commodity);
    }
}
