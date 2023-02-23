package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Model.CommodityByIdModel;
import Baloot.RequestMethod;
import Baloot.Route;

import Baloot.Model.ReportCommodityModel;

@Route("getCommodityById")
public class GetCommodityById extends Command{
    @AcceptMethod(RequestMethod.GET)
    public ReportCommodityModel handle(CommodityByIdModel input) throws Exception, CommodityNotFound {
        Commodity commodity = ContextManager.getCommodity(input.id);
        return commodity.getReportModel();
    }
}