package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("rateCommodity")
public class RateCommodity {
    @AcceptMethod(RequestMethod.GET)
    public String handle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        if (model.score < 1 || model.score > 10) {
            throw new InvalidRateScore();
        }
        ContextManager.getUser(model.username);
        Commodity commodity = ContextManager.getCommodity(model.commodityId);
        commodity.addRate(model.username, model.score);
        return "commodity rated successfully";
    }
}
