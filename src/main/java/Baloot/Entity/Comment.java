package Baloot.Entity;

import Baloot.Model.CommentModel;

import java.util.Date;
import java.util.HashMap;

public class Comment {private static Integer idCounter = 1;

    Integer id;
    Integer commodityId;
    String userName;
    String text;
    Date date;
    HashMap<String, Integer> votes = new HashMap<>();
    Integer likes = 0;
    Integer dislikes = 0;

    public Integer getId() {
        return id;
    }

    public String getuserName() {
        return userName;
    }

    public String getText() {
        return text;
    }
    public Date getDate(){
        return date;
    }

    public Comment(CommentModel model) {
        super();
        id = idCounter++;
        commodityId = model.commodityId;
        userName = model.userName;
        text = model.text;
        date = new Date();
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

}
