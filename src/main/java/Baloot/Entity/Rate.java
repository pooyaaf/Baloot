package Baloot.Entity;

import Baloot.Model.CommentModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(RateId.class)
public class Rate {
    @Getter
    @Id
    int commodityId;
    @Getter
    @Id
    String username;
    @Getter
    Integer rateNumber;
    public Rate(int commodityId, String username, Integer rateNumber) {
        this.commodityId = commodityId;
        this.username = username;
        this.rateNumber = rateNumber;
    }
}
