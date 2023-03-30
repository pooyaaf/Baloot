package Baloot.Model;

import Baloot.View.CommodityShortModel;
import Baloot.View.Component;

import java.util.ArrayList;

public class CommoditiesListByCategoryModel implements Component {
    public ArrayList<CommodityShortModel> commoditiesListByCategory;

    @Override
    public String render() {
        String result = "<table>";

        for (CommodityShortModel commodityShortModel : commoditiesListByCategory) {
            result += commodityShortModel.render();
        }

        result += "</table>";

        return result;
    }
}
