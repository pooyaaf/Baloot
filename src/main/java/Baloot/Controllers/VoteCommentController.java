package Baloot.Controllers;

import Baloot.Commands.RemoveFromBuyList;
import Baloot.Commands.VoteComment;
import Baloot.Context.UserContext;
import Baloot.Exception.*;
import Baloot.Model.CommodityBuyListModel;
import Baloot.View.VoteCommentModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/voteComment")
public class VoteCommentController {
    @PostMapping("/{commentId}")
    public void voteComment(@PathVariable Integer commentId, @RequestParam("comment_id") Integer vote) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            VoteComment command = new VoteComment();
            VoteCommentModel model = new VoteCommentModel();
            model.vote = vote;
            model.commentId = commentId;
            model.username = UserContext.username;
            command.handle(model);
        } catch (UserNotFound | UserNotAuthenticated | CommodityNotFound | CommentNotFound | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
