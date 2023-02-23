package Baloot.Entity;

import Baloot.Model.ProviderModel;
import Baloot.Model.UserModel;
import lombok.Getter;
import lombok.Setter;

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

    public Provider(ProviderModel model) {
        super();
        id = model.id;
        name = model.name;
        registryDate = model.registryDate;
    }

}
