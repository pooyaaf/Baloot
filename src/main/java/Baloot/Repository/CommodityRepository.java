package Baloot.Repository;

import Baloot.Entity.Commodity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommodityRepository  extends CrudRepository<Commodity, Integer> {
//    List<Commodity> findByRateAsc();
//    List<Commodity> findByOrderByReleaseDateAsc();
//    List<Commodity> findByCategories(String categories);
//    List<Commodity> findByProviderId(Integer providerId);
//    List<Commodity> findByNameContains(String name);
}
