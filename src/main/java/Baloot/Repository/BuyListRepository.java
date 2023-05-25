package Baloot.Repository;


import Baloot.Entity.BuyList;
import Baloot.Entity.BuyListId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyListRepository  extends CrudRepository<BuyList, BuyListId> {
}
