package Baloot.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class VoteId implements Serializable {
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
