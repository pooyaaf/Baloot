package Baloot.Entity;

import Baloot.Context.ContextManager;
import Baloot.Model.CommentInputModel;
import Baloot.Model.CommentModel;
import Baloot.Model.CommentReportModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

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
@Getter
public class Comment {
    private static Integer idCounter = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "commodityId", nullable = false)
    private Commodity commodity;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;
    String text;
    Date date;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voteID.comment")
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

    @SneakyThrows
    public Comment(CommentModel model) {
        super();
        id = idCounter++;
        commodity = ContextManager.getInstance().getCommodity(model.commodityId);
        user = ContextManager.getInstance().getUser(model.user.getUsername());
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
        Vote newVote = new Vote(this, user, vote);
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
        commentReportModel.username = user.getUsername();
        commentReportModel.text = text;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        commentReportModel.date = formatter.format(date);
        commentReportModel.like = likes;
        commentReportModel.dislike = dislikes;
        return commentReportModel;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Integer getCommodityId() {
        return commodity.getId();
    }
}
