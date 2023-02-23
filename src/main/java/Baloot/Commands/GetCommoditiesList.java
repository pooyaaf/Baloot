package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Model.CommodityListModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;

@Route("getCommoditiesList")
public class GetCommoditiesList extends Command{
    @AcceptMethod(RequestMethod.GET)
    public CommodityListModel handle(){
        Collection<Commodity> commodities = ContextManager.getAllCommodities();
        CommodityListModel result = new CommodityListModel();
        result.CommodityList = new ArrayList<>();
        for (Commodity commodity : commodities) {
            result.CommodityList.add(commodity.getModel());
        }
        return result;
    }
}
