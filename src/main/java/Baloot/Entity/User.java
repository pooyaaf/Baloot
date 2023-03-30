package Baloot.Entity;

import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CreditNotEnough;
import Baloot.Model.CommodityModel;
import Baloot.Model.UserModel;
import Baloot.View.UserInfoModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class User {
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String birthDate;
    @Setter
    @Getter
    private String address;
    @Setter
    @Getter
    private int credit;
    private HashMap<Integer, Commodity> buyList;
    private HashMap<Integer, Commodity> purchasedList;
    private HashMap<Integer, Integer> buyListInStock;
    private HashMap<Integer, Integer> purchasedListInStock;

    public User(UserModel model) {
        super();
        username = model.username;
        password = model.password;
        email = model.email;
        birthDate = model.birthDate;
        address = model.address;
        credit = model.credit;
        buyList = new HashMap<>();
        purchasedList = new HashMap<>();
        buyListInStock = new HashMap<>();
        purchasedListInStock = new HashMap<>();
    }

    public void addToBuyList(Commodity commodity) {
        if (buyListInStock.containsKey(commodity.getId())) {
            buyListInStock.put(commodity.getId(), buyListInStock.get(commodity.getId()) + 1);
        }
        else {
            buyListInStock.put(commodity.getId(), 1);
            buyList.put(commodity.getId(), commodity);
        }
    }

    public void removeFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
        if (!buyList.containsKey(commodity.getId()))
            throw new CommodityIsNotInBuyList();
        Integer inStockBuyList = buyListInStock.get(commodity.getId());
        if (inStockBuyList == 1) {
            buyListInStock.remove(commodity.getId());
            buyList.remove(commodity.getId());
        }
        else {
            buyListInStock.put(commodity.getId(), buyListInStock.get(commodity.getId()) - 1);
        }
    }

    public Collection<Commodity> getBuyList() {
        return buyList.values();
    }

    public UserInfoModel getUserInfoModel() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.userModel = new UserModel();
        userInfoModel.buyList = new ArrayList<>();
        userInfoModel.purchasedList = new ArrayList<>();
        userInfoModel.userModel.username = username;
        userInfoModel.userModel.password = password;
        userInfoModel.userModel.credit = credit;
        userInfoModel.userModel.birthDate = birthDate;
        userInfoModel.userModel.email = email;
        userInfoModel.userModel.address = address;

        userInfoModel.buyList = getBuyListModel();
        userInfoModel.purchasedList = getPurchasedListModel();
        return userInfoModel;
    }

    public void addCredit(int credit) {
        if (credit > 0) {
            this.credit += credit;
        }
    }

    private double calculatePayment() throws CreditNotEnough {
        double paymentPrice = 0;
        for (Commodity commodity : buyList.values()) {
            paymentPrice += commodity.getPrice() * buyListInStock.get(commodity.getId());
        }
        if (paymentPrice > credit) {
            throw new CreditNotEnough();
        }
        return paymentPrice;
    }

    public void payment() throws CreditNotEnough {
        credit -= calculatePayment();
        purchasedList.putAll(buyList);
        for (Integer id : buyListInStock.keySet()) {
            Integer newInStock = 0;
            if (purchasedListInStock.containsKey(id)) {
                newInStock += purchasedListInStock.get(id);
            }
            newInStock += buyListInStock.get(id);
            purchasedListInStock.put(id, newInStock);
        }
        buyList.clear();
        buyListInStock.clear();;
    }

    private ArrayList<CommodityModel> getBuyListModel() {
        ArrayList<CommodityModel> result = new ArrayList<>();
        for (Commodity commodity : buyList.values()) {
            CommodityModel commodityModel = commodity.getModel();
            commodityModel.inStock = buyListInStock.get(commodity.getId());
            result.add(commodityModel);
        }
        return result;
    }

    private ArrayList<CommodityModel> getPurchasedListModel() {
        ArrayList<CommodityModel> result = new ArrayList<>();
        for (Commodity commodity : purchasedList.values()) {
            CommodityModel commodityModel = commodity.getModel();
            commodityModel.inStock = purchasedListInStock.get(commodity.getId());
            result.add(commodityModel);
        }
        return result;
    }
}
