package Baloot.Model;

import Baloot.Entity.Comment;

import java.util.Date;

public class CommentViewModel {
    public Integer commentId;
    public String userName;
    public Date date;
    public String text;
    public Integer like;
    public Integer dislike;

    /*remove userName and replace with userModel*/

    public CommentViewModel(Comment comment) {
        super();
        commentId = comment.getId();
        userName = comment.getuserName();
        text = comment.getText();
        date = comment.getDate();
        like = comment.getLikes();
        dislike = comment.getDislikes();
    }
}
