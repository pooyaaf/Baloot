package Baloot.Model;

import org.jetbrains.annotations.Nullable;

public class VoteCommentModel {
    public String userName;
    public Integer commentId;
    @Nullable
    public Integer vote;

    public boolean isVoteCorrect() {
        return vote != null && (vote == 1 || vote == 0 || vote == -1);
    }
}
