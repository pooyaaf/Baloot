package Baloot.Repository;

import Baloot.Entity.Commodity;
import Baloot.Entity.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommodityRepository  extends CrudRepository<Commodity, Integer> {
    Iterable<Commodity> findAllByProvider(Provider provider);

//    Iterable<Commodity> findAllByCategoriesContainsIgnoreCase(String category);
//
//    Iterable<Commodity> findAllByNameIsContainingIgnoreCase(String str);
//
//    Iterable<Commodity> findAllByPriceIsBetween(Long from, Long to);
}
