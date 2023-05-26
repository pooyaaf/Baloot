package Baloot.service;


import Baloot.Entity.*;
import Baloot.Exception.CommodityIsNotInBuyList;
import Baloot.Repository.BuyListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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
        buyList.setInStock(buyList.getInStock() - 1);
        repository.save(buyList);
        if (inStock == 1) {
            repository.deleteBuyListByBuyListId(buyList.getBuyListId());
        }
    }

    public Iterable<BuyList> getBuyList(User user) {return repository.findAllByBuyListId_User(user);}

    public void clearUserBuyList(User user) {
        Iterable<BuyList> buyLists = repository.findAllByBuyListId_User(user);
        for (BuyList buyList : buyLists) {
            buyList.setInStock(0);
            repository.save(buyList);
            repository.deleteBuyListByBuyListId(buyList.getBuyListId());
        }
    }
}
