package Baloot.Repository;

import Baloot.Entity.Commodity;
import Baloot.Entity.Provider;
import Baloot.View.ProviderViewModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Integer> {

}
