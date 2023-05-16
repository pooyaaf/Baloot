package Baloot.Entity;

import Baloot.Model.ProviderModel;
import Baloot.View.ProviderViewModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Provider {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String registryDate;
    @Getter
    @Setter
    private String image;
    private HashMap<Integer, Commodity> commodities;

    public Provider(ProviderModel model) {
        super();
        id = model.id;
        name = model.name;
        registryDate = model.registryDate;
        image = model.image;
        commodities = new HashMap<>();
    }

    public void addCommodity(Commodity commodity) {
        commodities.put(commodity.getId(), commodity);
    }

    public Collection<Commodity> getAllCommodity() {
        return commodities.values();
    }

    public ProviderViewModel GetProviderViewModel() {
        ProviderViewModel providerViewModel = new ProviderViewModel();
        providerViewModel.providerModel = new ProviderModel();
        providerViewModel.providerModel.id = id;
        providerViewModel.providerModel.name = name;
        providerViewModel.providerModel.registryDate = registryDate;
        providerViewModel.providerModel.image = image;
        providerViewModel.commoditiesList = new ArrayList<>();
        for (Commodity commodity : commodities.values()) {
            providerViewModel.commoditiesList.add(commodity.getModel());
        }
        return providerViewModel;
    }
}
