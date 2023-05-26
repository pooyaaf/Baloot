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
    private PurchasedListId purchasedListId;



    Integer inStock;
    public PurchasedList(Commodity commodity, User user, Integer inStock) {
        this.purchasedListId = new PurchasedListId();
        this.purchasedListId.setCommodity(commodity);
        this.purchasedListId.setUser(user);
        this.inStock = inStock;
    }

    public String getUsername() {
        return purchasedListId.getUser().getUsername();
    }

    public Commodity getCommodity() {
        return purchasedListId.getCommodity();
    }
}
