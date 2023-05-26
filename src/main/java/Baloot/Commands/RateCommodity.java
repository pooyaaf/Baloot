package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.View.CommodityShortModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("rateCommodity/{username}/{commodityId}/{rate}")
public class RateCommodity extends Command {
    public CommodityShortModel handle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        return null;
    }

    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel GetHandle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        return handle(model);
    }

    @AcceptMethod(RequestMethod.POST)
    public CommodityShortModel PostHandle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        return handle(model);
    }
}
