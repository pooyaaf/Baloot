package Baloot.Entity;

import Baloot.Model.CommentInputModel;
import Baloot.Model.CommentModel;
import Baloot.Model.CommentReportModel;
import lombok.Getter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Comment {
    private static Integer idCounter = 1;

    Integer id;
    @Getter
    Integer commodityId;
    @Getter
    User user;
    String text;
    Date date;
    HashMap<String, Integer> votes = new HashMap<>();
    Integer likes = 0;
    Integer dislikes = 0;

    public Integer getId() {
        return id;
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
        this.user = model.user;
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
        commentReportModel.id = id;
        commentReportModel.username = user.getUsername();
        commentReportModel.text = text;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        commentReportModel.date = formatter.format(date);
        commentReportModel.like = likes;
        commentReportModel.dislike = dislikes;
        return commentReportModel;
    }

}
