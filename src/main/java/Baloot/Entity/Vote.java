package Baloot.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer voteID;
    Integer commentId;
    String username;
    Integer voteNumber;
    public Vote(Integer commentId, String username, Integer voteNumber) {
        this.commentId = commentId;
        this.username = username;
        this.voteNumber = voteNumber;
    }
}
