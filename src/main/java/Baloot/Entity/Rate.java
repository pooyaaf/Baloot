package Baloot.Entity;

import Baloot.Model.CommentModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rateId;
    @ManyToOne
    private Commodity commodity;
    String username;
    Integer rateNumber;
    public Rate(Commodity commodity, String username, Integer rateNumber) {
        this.commodity = commodity;
        this.username = username;
        this.rateNumber = rateNumber;
    }
}
