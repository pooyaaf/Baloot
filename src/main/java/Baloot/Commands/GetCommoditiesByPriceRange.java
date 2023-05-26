package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Model.CommoditiesListByPriceRangeModel;
import Baloot.View.CommodityListModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;

@Route("commodities/search/{start_price}/{end_price}")
public class GetCommoditiesByPriceRange extends Command {
    @AcceptMethod(RequestMethod.GET)
    public CommodityListModel handle(CommoditiesListByPriceRangeModel input){
        Iterable<Commodity> commodities = ContextManager.getInstance().getAllCommodities();
        CommodityListModel result = new CommodityListModel();
        result.commoditiesList = new ArrayList<>();
        for (Commodity commodity : commodities) {
            if (commodity.checkPriceRange(input.start_price, input.end_price)) {
                result.commoditiesList.add(commodity.getModel());
            }
        }
        return result;
    }
}
