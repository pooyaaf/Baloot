package Baloot.Entity;

import java.io.Serializable;
import java.util.Objects;

public class VoteId implements Serializable {
    private Integer commentId;

    private String username;

    public VoteId(Integer commentId, String username) {
        this.commentId = commentId;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(commentId, voteId.commentId) && Objects.equals(username, voteId.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, username);
    }
}