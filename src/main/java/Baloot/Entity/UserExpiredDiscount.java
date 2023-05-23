package Baloot.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "UserExpiredDiscount")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserExpiredDiscount {
    @ManyToOne
    @Getter
    private User user;

    @Id
    @Getter
    private String discountCode;

    public UserExpiredDiscount(User user, String discountCode) {
        this.user = user;
        this.discountCode = discountCode;
    }
}
