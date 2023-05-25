package Baloot.Entity;

import Baloot.Model.CommentInputModel;
import Baloot.Model.CommentModel;
import Baloot.Model.CommentReportModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    private static Integer idCounter = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Getter
    Integer commodityId;
    @Getter
    String username;
    String text;
    Date date;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "commentId")
    private Set<Vote> votes;
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
        username = model.user.getUsername();
        text = model.text;
        date = model.date;
        votes = new HashSet<>();
    }

    public void addVote(Integer vote, String userName) {
        for (Vote voteObj : votes) {
            if (voteObj.getUsername().equals(userName)) {
                Integer preVote = voteObj.getVoteNumber();
                if (preVote == 1) {
                    likes--;
                }
                if (preVote == -1) {
                    dislikes--;
                }
            }
        }
        Vote newVote = new Vote(this.id, userName, vote);
        votes.add(newVote);
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
        commentReportModel.username = username;
        commentReportModel.text = text;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        commentReportModel.date = formatter.format(date);
        commentReportModel.like = likes;
        commentReportModel.dislike = dislikes;
        return commentReportModel;
    }

}
