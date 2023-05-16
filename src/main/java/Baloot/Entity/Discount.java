package Baloot.Entity;

import Baloot.Model.DiscountModel;
import lombok.Getter;

public class Discount {
    @Getter
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
