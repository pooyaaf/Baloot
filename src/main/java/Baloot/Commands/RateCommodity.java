package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommentNotFound;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.view.VoteCommentModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("rateCommodity/{username}/{commodityId}/{rate}")
public class RateCommodity extends Command {
    public CommodityShortModel handle(RateModel model) throws Exception, UserNotFound, CommodityNotFound {
        if (model.rate < 1 || model.rate > 10) {
            throw new InvalidRateScore();
        }
        ContextManager.getInstance().getUser(model.username);
        Commodity commodity = ContextManager.getInstance().getCommodity(model.commodityId);
        commodity.addRate(model.username, model.rate);
        return commodity.getReportModel();
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
