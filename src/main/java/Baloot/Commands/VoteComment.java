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
import Baloot.View.CommodityShortModel;
import Baloot.View.VoteCommentModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.Optional;

@Route("voteComment/{username}/{commentId}/{vote}")
public class VoteComment extends Command {


    public CommodityShortModel handle(VoteCommentModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        if (!model.isVoteCorrect()) {
            throw new InvalidVoteValue();
        }
        User user = ContextManager.getInstance().getUser(model.username);
//        Comment comment = ContextManager.getInstance().getComment(model.commentId);
//        comment.addVote(model.vote, user.getUsername());
//        Commodity commodity = ContextManager.getInstance().getCommodity(comment.getCommodityId());
//        return commodity.getReportModel();
        return null;
    }
    @AcceptMethod(RequestMethod.GET)
    public CommodityShortModel handleGet(VoteCommentModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        return handle(model);
    }

    @AcceptMethod(RequestMethod.POST)
    public CommodityShortModel handlePost(VoteCommentModel model) throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
        return handle(model);
    }
}
