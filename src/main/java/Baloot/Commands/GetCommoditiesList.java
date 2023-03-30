package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.View.CommodityListModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;

@Route("commodities")
public class GetCommoditiesList extends Command{
    @AcceptMethod(RequestMethod.GET)
    public CommodityListModel handle(){
        Collection<Commodity> commodities = ContextManager.getInstance().getAllCommodities();
        CommodityListModel result = new CommodityListModel();
        result.commoditiesList = new ArrayList<>();
        for (Commodity commodity : commodities) {
            result.commoditiesList.add(commodity.getModel());
        }
        return result;
    }
}
