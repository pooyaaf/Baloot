package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Model.CommodityByIdModel;
import Baloot.RequestMethod;
import Baloot.Route;

import Baloot.Model.view.CommodityShortModel;

@Route("commodities/{commodity_id}")
public class GetCommodityById extends Command{
    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel handle(CommodityByIdModel input) throws Exception, CommodityNotFound {
        Commodity commodity = ContextManager.getInstance().getCommodity(Integer.parseInt(input.commodity_id));
        return commodity.getReportModel();
    }
}