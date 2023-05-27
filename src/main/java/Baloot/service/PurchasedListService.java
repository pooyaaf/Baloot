package Baloot.service;

import Baloot.Entity.*;
import Baloot.Repository.PurchasedListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchasedListService {
    private final PurchasedListRepository repository;
    public void addPurchasedList(Iterable<BuyList> buyLists) {
        for (BuyList buyList : buyLists) {
            PurchasedListId purchasedListId = new PurchasedListId();
            purchasedListId.setCommodity(buyList.getCommodity());
            purchasedListId.setUser(buyList.getBuyListId().getUser());
            Optional<PurchasedList> purchasedListOptional = repository.findById(purchasedListId);
            if (!purchasedListOptional.isEmpty()) {
                PurchasedList purchasedList = purchasedListOptional.get();
                purchasedList.setInStock(purchasedListOptional.get().getInStock() + buyList.getInStock());
                repository.save(purchasedList);
            }
            else {
                PurchasedList purchasedList = new PurchasedList(buyList.getCommodity(), buyList.getBuyListId().getUser(), buyList.getInStock());
                repository.save(purchasedList);
            }
        }
    }
}
