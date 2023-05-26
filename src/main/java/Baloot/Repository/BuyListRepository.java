package Baloot.Repository;


import Baloot.Entity.BuyList;
import Baloot.Entity.BuyListId;
import Baloot.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyListRepository  extends CrudRepository<BuyList, BuyListId> {
    void deleteBuyListByBuyListId(BuyListId buyListId);
    void deleteBuyListByBuyListId_User(User user);
    Iterable<BuyList> findAllByBuyListId_User(User user);
}
