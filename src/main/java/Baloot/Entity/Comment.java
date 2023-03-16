package Baloot.Entity;

import Baloot.Model.CommentModel;
import Baloot.Model.CommentReportModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;

public class Comment {
    private static Integer idCounter = 1;

    Integer id;
    @Getter
    Integer commodityId;
    String userEmail;
    String text;
    Date date;
    HashMap<String, Integer> votes = new HashMap<>();
    Integer likes = 0;
    Integer dislikes = 0;

    public Integer getId() {
        return id;
    }

    public String getuserName() {
        return userEmail;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public Comment(CommentModel model) {
        super();
        id = idCounter++;
        commodityId = model.commodityId;
        userEmail = model.userEmail;
        text = model.text;
        date = model.date;
    }

    public void addVote(Integer vote, String userName) {
        if (votes.containsKey(userName)) {
            Integer preVote = votes.get(userName);
            if (preVote == 1)
                likes--;
            if (preVote == -1)
                dislikes--;
        }
        votes.put(userName, vote);
        if (vote == 1)
            likes++;
        if (vote == -1)
            dislikes++;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public CommentReportModel getReportModel() {
        CommentReportModel commentReportModel = new CommentReportModel();
        commentReportModel.userEmail = userEmail;
        commentReportModel.text = text;
        commentReportModel.date = date;
        commentReportModel.like = likes;
        commentReportModel.dislike = dislikes;
        return commentReportModel;
    }

}
