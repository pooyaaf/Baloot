package Baloot.View;

import Baloot.Model.CommodityModel;
import Baloot.Model.UserModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserInfoModel {
    public UserModel userModel;
    public ArrayList<CommodityModel> buyList;
    public ArrayList<CommodityModel> purchasedList;
    public double buyListPrice;
}
