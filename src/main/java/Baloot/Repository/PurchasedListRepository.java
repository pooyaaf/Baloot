package Baloot.Repository;

import Baloot.Entity.*;
import org.springframework.data.repository.CrudRepository;

public interface PurchasedListRepository   extends CrudRepository<PurchasedList, PurchasedListId> {
    void deletePurchasedListByPurchasedListId(PurchasedListId purchasedListId);
    Iterable<PurchasedList> findAllByPurchasedListId_User(User user);
}
