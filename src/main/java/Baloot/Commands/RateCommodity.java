package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("rateCommodity/{username}/{commodityId}/{rate}")
public class RateCommodity extends Command {
    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel handle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        if (model.rate < 1 || model.rate > 10) {
            throw new InvalidRateScore();
        }
        ContextManager.getUser(model.username);
        Commodity commodity = ContextManager.getCommodity(model.commodityId);
        commodity.addRate(model.username, model.rate);
        return commodity.getReportModel();
    }
}
