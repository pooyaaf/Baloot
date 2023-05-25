package Baloot.Entity;

import Baloot.Context.ContextManager;
import Baloot.Exception.CommodityNotFound;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "purchasedList")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(PurchasedListId.class)
public class PurchasedList {
    @Getter
    @Id
    Integer commodityId;
    @Getter
    @Id
    String username;
    @Getter
    @Setter
    Integer inStock;
    public PurchasedList(Commodity commodity, String username, Integer inStock) {
        this.username = username;
        this.inStock = inStock;
        this.commodityId = commodity.getId();
    }

    public Commodity getCommodity() {
        try {
            return ContextManager.getInstance().getCommodity(commodityId);
        } catch (Exception | CommodityNotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
