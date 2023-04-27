package Baloot.View;

public class VoteCommentModel {
    public String username;
    public Integer commentId;
    public Integer vote;

    public boolean isVoteCorrect() {
        return vote != null && (vote == 1 || vote == 0 || vote == -1);
    }

}
