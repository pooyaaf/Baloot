package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Comment;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommentNotFound;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.view.VoteModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("voteComment/{username}/{commentId}/{vote}")
public class voteComment {
    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel handle(VoteModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        User user = ContextManager.getUser(model.username);
        Comment comment = ContextManager.getComment(model.commentId);
        comment.addVote(model.vote, user.getUsername());
        Commodity commodity = ContextManager.getCommodity(comment.getCommodityId());
        return commodity.getReportModel();
    }
}
