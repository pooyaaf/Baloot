package Baloot.Entity;

import Baloot.Model.DiscountModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "discount")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount {
    @Getter
    @Id
    private String discountCode;
    @Getter
    private Integer discount;
    public Discount(DiscountModel model) {
        discountCode = model.discountCode;
        discount = model.discount;
    }

    public double toPercent() {
        return (double) discount / 100;
    }
    public DiscountModel getModel() {
        DiscountModel model = new DiscountModel();
        model.discountCode = discountCode;
        model.discount = discount;
        return model;
    }
}
