package Baloot.Entity;

import Baloot.Model.ProviderModel;
import Baloot.View.ProviderViewModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "provider")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Provider {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @Column(name="registrydate")
    private String registrydate;
    @Getter
    @Setter
    private String image;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "provider")
    private Map<Integer, Commodity> commodities;

    public Provider(ProviderModel model) {
        super();
        id = model.id;
        name = model.name;
        registrydate = model.registryDate;
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
        providerViewModel.providerModel.registryDate = registrydate;
        providerViewModel.providerModel.image = image;
        providerViewModel.commoditiesList = new ArrayList<>();
        for (Commodity commodity : commodities.values()) {
            providerViewModel.commoditiesList.add(commodity.getModel());
        }
        return providerViewModel;
    }
}
