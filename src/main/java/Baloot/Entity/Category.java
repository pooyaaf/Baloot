package Baloot.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

public class Category {
    @Getter
    @Setter
    private String name;
    private HashSet<Commodity> commodities;

    public Category(String name) {
        this.name = name;
        commodities = new HashSet<>();
    }

    public HashSet<Commodity> getAllCommodity() {
        return commodities;
    }

    public void addCommodity(Commodity commodity) {
        commodities.add(commodity);
    }
}
