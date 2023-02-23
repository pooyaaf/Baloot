package Baloot.Entity;

import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Exception.UserNotFound;
import Baloot.Model.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

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
    private HashSet<Commodity> buyList;

    public User(UserModel model) {
        super();
        username = model.username;
        password = model.password;
        email = model.email;
        birthDate = model.birthDate;
        address = model.address;
        credit = model.credit;
        buyList = new HashSet<>();
    }

    public void addToBuyList(Commodity commodity) {
        buyList.add(commodity);
    }

    public void removeFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
        if (!buyList.contains(commodity))
            throw new CommodityIsNotInBuyList();
        buyList.remove(commodity);
    }

    public HashSet<Commodity> getBuyList() {
        return buyList;
    }
}
