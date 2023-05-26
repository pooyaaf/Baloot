package Baloot.Controllers;

import Baloot.Commands.AddComment;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Comment;
import Baloot.Entity.Discount;
import Baloot.Entity.User;
import Baloot.Exception.*;
import Baloot.Model.CommentModel;
import Baloot.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@AllArgsConstructor
@RequestMapping("/addComment")
public class AddCommentController {
    private final CommentService commentService;

    @PostMapping("/{commodityId}")
    public void addComment(@PathVariable Integer commodityId, @RequestParam("comment") String comment) {
        try {
            if (Authentication.isNotAuthenticated()) throw new UserNotAuthenticated();
            AddComment command = new AddComment();
            CommentModel commentModel = new CommentModel();
            commentModel.user = ContextManager.getInstance().getUser(UserContext.username);
            commentModel.commodityId = commodityId;
            commentModel.text = comment;
            LocalDateTime localDateTime = LocalDateTime.now();
            commentModel.date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            command.handle(commentModel);
            Comment newComment = new Comment(commentModel);
            commentService.addComment(newComment);
        } catch (UserNotFound | Exception | CommodityNotFound | CommodityNotInStuck | UserNotAuthenticated e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
