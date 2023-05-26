package Baloot.Context.Filter;

import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CategoryNotFound;

import java.util.ArrayList;
import java.util.Collection;

public class FilterManager {
    private String category;
    private String commodityName;
    private boolean isSorted;
    private static FilterManager instance;

    public static FilterManager getInstance() {
        if (instance == null) instance = new FilterManager();
        return instance;
    }

    private FilterManager() {
        reset();
    }
    public void addSearchByCategory(String category) {
        this.category = category;
    }

    public void addSearchByName(String commodityName) {
        this.commodityName = commodityName;
    }

    public void setSort() {
        this.isSorted = true;
    }

    private ArrayList<Commodity> filterByName(Iterable<Commodity> commodities) {
        ArrayList<Commodity> filtered = new ArrayList<>();
        for (Commodity commodity : commodities) {
            if (commodity.getName().contains(commodityName)) {
                filtered.add(commodity);
            }
        }
        return filtered;
    }

    public ArrayList<Commodity> filter(Iterable<Commodity> commodities) {
        ArrayList<Commodity> filtered = new ArrayList<>();
        try {
            if (category != null) {
                 commodities = ContextManager.getInstance().getCategory(category).getAllCommodity();
            }
            for (Commodity commodity : commodities) {
                filtered.add(commodity);
            }
            if (commodityName != null) {
                filtered = filterByName(commodities);
            }
            if (isSorted) {
                filtered.sort((first, second) -> {
                    if(first.getPrice() > second.getPrice()) return 1;
                    if(first.getPrice() < second.getPrice()) return -1;
                    return 0;
                });
            }
        }
        catch (CategoryNotFound e) {
            return filtered;
        }
        return filtered;
    }

    public void reset() {
        category = null;
        commodityName = null;
        isSorted = false;
    }
}
