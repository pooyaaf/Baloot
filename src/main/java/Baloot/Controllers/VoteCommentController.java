package Baloot.Controllers;

import Baloot.Commands.RemoveFromBuyList;
import Baloot.Commands.VoteComment;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Comment;
import Baloot.Entity.User;
import Baloot.Entity.Vote;
import Baloot.Entity.VoteId;
import Baloot.Exception.*;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Repository.VoteRepository;
import Baloot.View.VoteCommentModel;
import Baloot.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/voteComment")
public class VoteCommentController {
    @Autowired
    private final VoteRepository voteRepository;

    @Autowired
    private final CommentService commentService;

    @PostMapping("/{commentId}")
    void handle(VoteCommentModel model) throws Exception, UserNotFound, CommentNotFound {
        if (!model.isVoteCorrect()) {
            throw new InvalidVoteValue();
        }
        User user = ContextManager.getInstance().getUser(model.username);
        Optional<Comment> optionalComment = commentService.getCommentById(model.commentId);
        if (optionalComment.isEmpty()) throw new CommentNotFound();
        Comment comment = optionalComment.get();
        Vote vote = new Vote(comment, user, model.vote);
        voteRepository.save(vote);
        Iterable<Vote> votes = voteRepository.findAllByVoteID_Comment(comment);
        Integer like = 0;
        Integer dislike = 0;
        for (Vote vote1 : votes) {
            Integer voteNumber = vote1.getVoteNumber();
            if (voteNumber == 1) like++;
            else if (voteNumber == -1) dislike++;
        }
        comment.updateVotes(like, dislike);
        commentService.addComment(comment);
    }
    public void voteComment(@PathVariable Integer commentId, @RequestParam("comment_id") Integer vote) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            VoteCommentModel model = new VoteCommentModel();
            model.vote = vote;
            model.commentId = commentId;
            model.username = UserContext.username;
            handle(model);
        } catch (UserNotFound | UserNotAuthenticated | CommentNotFound | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
