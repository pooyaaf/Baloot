package Baloot.Model;

import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.view.Component;

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
