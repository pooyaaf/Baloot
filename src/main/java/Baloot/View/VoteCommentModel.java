package Baloot.View;

import org.jetbrains.annotations.Nullable;

public class VoteCommentModel {
    public String username;
    public Integer commentId;
    @Nullable
    public Integer vote;

    public boolean isVoteCorrect() {
        return vote != null && (vote == 1 || vote == 0 || vote == -1);
    }

}
