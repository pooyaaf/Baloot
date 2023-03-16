package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Comment;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommentNotFound;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidVoteValue;
import Baloot.Exception.UserNotFound;
import Baloot.Model.view.CommodityShortModel;
import Baloot.Model.view.VoteCommentModel;
import Baloot.RequestMethod;
import Baloot.Route;

@Route("/voteComment/{username}/{commentId}/{vote}")
public class voteComment extends Command {

    public CommodityShortModel handle(VoteCommentModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        if (!model.isVoteCorrect()) {
            throw new InvalidVoteValue();
        }
        User user = ContextManager.getUser(model.username);
        Comment comment = ContextManager.getComment(model.commentId);
        comment.addVote(model.vote, user.getUsername());
        Commodity commodity = ContextManager.getCommodity(comment.getCommodityId());
        return commodity.getReportModel();
    }
    @AcceptMethod(RequestMethod.GET)
    public void handleGet(VoteCommentModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        handle(model);
    }

    @AcceptMethod(RequestMethod.POST)
    public void handlePost(VoteCommentModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        handle(model);
    }

}
