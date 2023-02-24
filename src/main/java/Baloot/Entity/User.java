package Baloot.Entity;

import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Model.UserModel;
import lombok.Getter;
import lombok.Setter;

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

    public User(UserModel model) {
        super();
        username = model.username;
        password = model.password;
        email = model.email;
        birthDate = model.birthDate;
        address = model.address;
        credit = model.credit;
        buyList = new HashMap<>();
    }

    public void addToBuyList(Commodity commodity) {
        buyList.put(commodity.getId(), commodity);
    }

    public void removeFromBuyList(Commodity commodity) throws CommodityIsNotInBuyList {
        if (!buyList.containsKey(commodity.getId()))
            throw new CommodityIsNotInBuyList();
        buyList.remove(commodity.getId());
    }

    public Collection<Commodity> getBuyList() {
        return buyList.values();
    }
}
