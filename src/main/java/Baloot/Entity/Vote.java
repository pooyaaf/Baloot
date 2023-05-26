package Baloot.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Data
public class Vote {
    @EmbeddedId
    private VoteId voteID;



    Integer voteNumber;
    public Vote( Integer voteNumber) {

        this.voteNumber = voteNumber;
    }

    public String getUsername() {
        return voteID.getUser().getUsername();
    }
}
