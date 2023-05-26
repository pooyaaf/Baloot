package Baloot.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "buyList")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Data
public class BuyList {
    @EmbeddedId
    private BuyListId buyListId;



    Integer inStock;
    public BuyList(Commodity commodity, User user, Integer inStock) {
        this.buyListId = new BuyListId();
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
