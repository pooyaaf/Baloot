package Baloot.service;


import Baloot.Entity.*;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Repository.BuyListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyListService {
    private final BuyListRepository repository;
    public void addBuyList(User user, Commodity commodity) {
        BuyListId buyListId = new BuyListId();
        buyListId.setUser(user);
        buyListId.setCommodity(commodity);
        Optional<BuyList> optionalBuyList = repository.findById(buyListId);
        if (!optionalBuyList.isEmpty()) {
            BuyList buyList = optionalBuyList.get();
            buyList.setInStock(optionalBuyList.get().getInStock() + 1);
            repository.save(buyList);
        }
        else
            repository.save(new BuyList(commodity, user, 1));
    }
    public void removeFromBuyList(User user, Commodity commodity) throws CommodityIsNotInBuyList {
        BuyListId buyListId = new BuyListId();
        buyListId.setUser(user);
        buyListId.setCommodity(commodity);
        Optional<BuyList> optionalBuyList = repository.findById(buyListId);
        if (optionalBuyList.isEmpty()) throw new CommodityIsNotInBuyList();
        BuyList buyList = optionalBuyList.get();
        Integer inStock = buyList.getInStock();
        if (inStock == 1) {
            repository.deleteById(buyList.getBuyListId());
        }
        else {
            buyList.setInStock(buyList.getInStock() - 1);
            repository.save(buyList);
        }
    }
}