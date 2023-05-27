package Baloot.Controllers;

import Baloot.Commands.GetProviderById;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.Provider;
import Baloot.Exception.ProviderNotFound;
import Baloot.Model.CommodityModel;
import Baloot.Model.ProviderByIdModel;
import Baloot.Repository.CommodityRepository;
import Baloot.Repository.ProviderRepository;
import Baloot.View.ProviderViewModel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/providers")
public class ProviderController {
    @Autowired
    private final ProviderRepository providerRepository;
    @Autowired
    private final CommodityRepository commodityRepository;

    @GetMapping
    public Iterable<ProviderViewModel> all() {
        Iterable<Provider> providers = ContextManager.getInstance().getAllProviders();
        ArrayList providerViewModelList = new ArrayList<>();
        for (Provider provider : providers) {
            providerViewModelList.add(provider.GetProviderViewModel());
        }
        return providerViewModelList;
    }

    @SneakyThrows
    @GetMapping("/{id}")
    public ProviderViewModel one(@PathVariable String id){
        try {
            GetProviderById getProviderById = new GetProviderById();
            ProviderByIdModel model = new ProviderByIdModel();
            model.provider_id = id;
            ProviderViewModel providerViewModel = providerRepository.findById(Integer.valueOf(id)).get().GetProviderViewModel();
            Provider provider = ContextManager.getInstance().getProvider(Integer.valueOf(id));
            List<Commodity> commodityIterable =(List<Commodity>) commodityRepository.findAllByProvider(provider);
             providerViewModel.commoditiesList = new ArrayList<>(commodityIterable.stream().map(o -> o.getModel()).toList());
             return providerViewModel;
        }
        catch ( Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
