package Baloot.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "purchasedList")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Data
public class PurchasedList {
    @EmbeddedId
    private BuyListId buyListId;



    Integer inStock;
    public PurchasedList(Commodity commodity, User user, Integer inStock) {
        this.buyListId.setCommodity(commodity);
        this.buyListId.setUser(user);
        this.inStock = inStock;
    }

    public String getUsername() {
        return buyListId.getUser().getUsername();
    }

    public Commodity getCommodity() {
        return buyListId.getCommodity();
    }
}
