package Baloot.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;

public class Category {
    @Getter
    @Setter
    private String name;
    private HashMap<Integer, Commodity> commodities;

    public Category(String name) {
        this.name = name;
        commodities = new HashMap<>();
    }

    public Collection<Commodity> getAllCommodity() {
        return commodities.values();
    }

    public void addCommodity(Commodity commodity) {
        commodities.put(commodity.getId(), commodity);
    }
}
