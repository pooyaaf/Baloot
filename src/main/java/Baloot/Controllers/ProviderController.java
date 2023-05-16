package Baloot.Controllers;

import Baloot.Commands.GetProviderById;
import Baloot.Context.ContextManager;
import Baloot.Context.Filter.FilterManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.Provider;
import Baloot.Exception.ProviderNotFound;
import Baloot.Model.ProviderByIdModel;
import Baloot.View.CommodityFullModel;
import Baloot.View.CommodityListModel;
import Baloot.View.ProviderViewModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping("/providers")
public class ProviderController {
    @GetMapping
    public Iterable<ProviderViewModel> all() {
        Collection<Provider> providers = ContextManager.getInstance().getAllProviders();
        ArrayList providerViewModelList = new ArrayList<>();
        for (Provider provider : providers) {
            providerViewModelList.add(provider.GetProviderViewModel());
        }
        return providerViewModelList;
    }

    @GetMapping("/{id}")
    public ProviderViewModel one(@PathVariable String id){
        try {
            GetProviderById getProviderById = new GetProviderById();
            ProviderByIdModel model = new ProviderByIdModel();
            model.provider_id = id;
            return getProviderById.handle(model);
        }
        catch (ProviderNotFound | Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
