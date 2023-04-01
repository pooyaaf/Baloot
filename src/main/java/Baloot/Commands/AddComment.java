package Baloot.Commands;

import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Comment;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommentInputModel;
import Baloot.Model.CommentModel;

public class AddComment extends Command{
    public void handle(CommentModel input) throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        Comment comment = new Comment(input);
        ContextManager.getInstance().putComment(comment.getId(), comment);
    }
}
