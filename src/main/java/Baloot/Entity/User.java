package Baloot.Entity;

import Baloot.Context.ContextManager;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.CreditNotEnough;
import Baloot.Exception.ExpiredDiscount;
import Baloot.Exception.DiscountNotFound;
import Baloot.Model.CommodityModel;
import Baloot.Model.UserModel;
import Baloot.View.UserInfoModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Setter
    @Getter
    @Id
    private String username;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    @Unique
    private String email;
    @Setter
    @Getter
    @Column(name="birthdate")
    private String birthdate;
    @Setter
    @Getter
    private String address;
    @Setter
    @Getter
    private int credit;

    @Setter
    @Getter
    private double buyListPrice;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private Set<UserExpiredDiscount> expiredDiscounts;

    @Column(name="activediscountcode")
    private String activediscountcode;

    public User(UserModel model) {
        super();
        username = model.username;
        password = model.password;
        email = model.email;
        birthdate = model.birthDate;
        address = model.address;
        credit = model.credit;
        expiredDiscounts = new HashSet<>();
        activediscountcode = null;
        buyListPrice = 0;
    }

    public UserInfoModel getUserInfoModel(Iterable<BuyList> buyLists, Iterable<PurchasedList> purchasedLists) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.userModel = new UserModel();
        userInfoModel.buyList = new ArrayList<>();
        userInfoModel.purchasedList = new ArrayList<>();
        userInfoModel.userModel.username = username;
        userInfoModel.userModel.password = password;
        userInfoModel.userModel.credit = credit;
        userInfoModel.userModel.birthDate = birthdate;
        userInfoModel.userModel.email = email;
        userInfoModel.userModel.address = address;

        userInfoModel.buyList = getBuyListModel(buyLists);
        userInfoModel.purchasedList = getPurchasedListModel(purchasedLists);
        userInfoModel.buyListPrice = buyListPrice;
        return userInfoModel;
    }


    public void addCredit(int credit) {
        if (credit > 0) {
            this.credit += credit;
        }
    }

    public double calculatePayment(Iterable<BuyList> buyLists) {
        double paymentPrice = 0;
        for (BuyList buyList : buyLists) {
            paymentPrice += buyList.getCommodity().getPrice() * buyList.getInStock();
        }
        if (activediscountcode == null) return paymentPrice;
        try {
            return paymentPrice * (1 - ContextManager.getInstance().getDiscount(activediscountcode).toPercent());
        } catch (DiscountNotFound e) {
            throw new RuntimeException(e);
        }
    }

    public void payment(Iterable<BuyList> buyLists) throws CreditNotEnough {
        double calculatedPayment = calculatePayment(buyLists);
        if (calculatedPayment > credit) {
            throw new CreditNotEnough();
        }
        credit -= calculatePayment(buyLists);
//        for (BuyList buyList : buyLists) {
//            Optional<PurchasedList> optionalBuyList = purchasedLists.stream().filter(obj -> obj.getCommodity().getId() == buyList.getCommodity().getId()).findFirst();
//            if (!optionalBuyList.isEmpty())
//                optionalBuyList.get().setInStock(optionalBuyList.get().getInStock() + buyList.getInStock());
//            else {
//                PurchasedList purchasedList = new PurchasedList(buyList.getCommodity(), this, buyList.getInStock());
//                purchasedLists.add(purchasedList);
//            }
//        }
//        buyLists.clear();
        updateUsedDiscounts();
    }

    private ArrayList<CommodityModel> getBuyListModel(Iterable<BuyList> buyLists) {
        ArrayList<CommodityModel> result = new ArrayList<>();
        for (BuyList buyList : buyLists) {
            Commodity commodity = buyList.getCommodity();
            CommodityModel commodityModel = commodity.getModel();
            commodityModel.inCart = buyList.getInStock();
            result.add(commodityModel);
        }
        return result;
    }

    private ArrayList<CommodityModel> getPurchasedListModel(Iterable<PurchasedList> purchasedLists) {
        ArrayList<CommodityModel> result = new ArrayList<>();
        for (PurchasedList purchasedList : purchasedLists) {
            CommodityModel commodityModel = purchasedList.getCommodity().getModel();
            commodityModel.inCart = purchasedList.getInStock();
            result.add(commodityModel);
        }
        return result;
    }

    private void updateUsedDiscounts() {
        if (activediscountcode == null) return;
        expiredDiscounts.add(new UserExpiredDiscount(this, activediscountcode));
        activediscountcode = null;
    }

    public void addDiscount(Discount discount) throws ExpiredDiscount {
        Optional<UserExpiredDiscount> optionalDiscount = expiredDiscounts.stream().filter(obj -> obj.getDiscountCode().equals(discount.getDiscountCode())).findFirst();
        if (!optionalDiscount.isEmpty()) throw new ExpiredDiscount();
        activediscountcode = discount.getDiscountCode();
    }
}
