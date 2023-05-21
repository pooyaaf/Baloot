package Baloot.Entity;

import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CreditNotEnough;
import Baloot.Exception.ExpiredDiscount;
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
    private HashMap<String, Discount> expiredDiscounts;
    private Discount activeDiscount;

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
        expiredDiscounts = new HashMap<>();
        activeDiscount = null;
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
        userInfoModel.buyListPrice = calculatePayment();
        return userInfoModel;
    }

    public void addCredit(int credit) {
        if (credit > 0) {
            this.credit += credit;
        }
    }

    private double calculatePayment() {
        double paymentPrice = 0;
        for (Commodity commodity : buyList.values()) {
            paymentPrice += commodity.getPrice() * buyListInStock.get(commodity.getId());
        }
        if (activeDiscount == null) return paymentPrice;
        return paymentPrice * (1 - activeDiscount.toPercent());
    }

    public void payment() throws CreditNotEnough {
        double calculatedPayment = calculatePayment();
        if (calculatedPayment > credit) {
            throw new CreditNotEnough();
        }
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
        buyListInStock.clear();
        updateUsedDiscounts();
    }

    private ArrayList<CommodityModel> getBuyListModel() {
        ArrayList<CommodityModel> result = new ArrayList<>();
        for (Commodity commodity : buyList.values()) {
            CommodityModel commodityModel = commodity.getModel();
            commodityModel.inCart = buyListInStock.get(commodity.getId());
            result.add(commodityModel);
        }
        return result;
    }

    private ArrayList<CommodityModel> getPurchasedListModel() {
        ArrayList<CommodityModel> result = new ArrayList<>();
        for (Commodity commodity : purchasedList.values()) {
            CommodityModel commodityModel = commodity.getModel();
            commodityModel.inCart = purchasedListInStock.get(commodity.getId());
            result.add(commodityModel);
        }
        return result;
    }

    private void updateUsedDiscounts() {
        if (activeDiscount == null) return;
        expiredDiscounts.put(activeDiscount.getDiscountCode(), activeDiscount);
        activeDiscount = null;
    }

    public void addDiscount(Discount discount) throws ExpiredDiscount {
        if (expiredDiscounts.containsKey(discount.getDiscountCode())) throw new ExpiredDiscount();
        activeDiscount = discount;
    }
}
