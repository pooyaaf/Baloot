package Baloot.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(VoteId.class)
public class Vote {
    @Getter
    @Id
    Integer commentId;
    @Getter
    @Id
    String username;
    @Getter
    Integer voteNumber;
    public Vote(Integer commentId, String username, Integer voteNumber) {
        this.commentId = commentId;
        this.username = username;
        this.voteNumber = voteNumber;
    }
}
