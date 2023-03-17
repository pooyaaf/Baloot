package Baloot.Model;

public class CommoditiesListByPriceRangeModel {

    public double start_price;
    public double end_price;

    public CommoditiesListByPriceRangeModel() {

    }

    public CommoditiesListByPriceRangeModel(double start_price, double end_price) {
        if (start_price < 0 || end_price < 0) {
            throw new IllegalArgumentException("Prices cannot be negative");
        }
        this.start_price = start_price;
        this.end_price = end_price;
    }
}
