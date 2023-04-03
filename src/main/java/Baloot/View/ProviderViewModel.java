package Baloot.View;

import Baloot.Model.CommodityModel;
import Baloot.Model.ProviderModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProviderViewModel {
    public ProviderModel providerModel;
    public ArrayList<CommodityModel> commoditiesList;
}
