package Baloot;

import Baloot.Commands.AddToBuyList;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotFound;
import Baloot.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class AddToBuyListTest {
    private Integer commodityId = 1;
    private String username = "user1";
    private Integer inStuck = 100;

    @Before
    public void setUp() throws Exception {
        // Generate users
        DataGenerator.GenerateUser("user1", "password1", "user1@example.com", "1990-01-01", "123 Main St", 1000);
        // Generate providers
        DataGenerator.GenerateProvider(1, "Provider A", "2023-02-23");
        // Generate commodities
        DataGenerator.GenerateCommodity(1, "Product A", 1, 10.0, new String[]{"Phone"}, 4.0, inStuck);

    }

    @After
    public void teardown() {
        ContextManager.resetContext();
    }

    @Test
    public void addToBuyList_ShouldSuccessful() throws CommodityNotFound, Exception, UserNotFound, CommodityNotInStuck {
        BuyModel buyModel = new BuyModel();
        buyModel.commodityId = commodityId;
        buyModel.username = username;
        AddToBuyList command = new AddToBuyList();

        command.handle(buyModel);
    }

    @Test
    public void addToBuyList_AfterBuyShouldInStuckDecreaseAndBugListContainExpectedCommodity() throws CommodityNotFound, Exception, UserNotFound, CommodityNotInStuck {
        BuyModel buyModel = new BuyModel();
        buyModel.commodityId = commodityId;
        buyModel.username = username;
        AddToBuyList command = new AddToBuyList();

        int beforeBuyInStuck = inStuck;

        command.handle(buyModel);

        Commodity commodity = ContextManager.getCommodity(commodityId);
        Collection<Commodity> buyList = ContextManager.getUser(username).getBuyList();
        int afterBuyInStuck = commodity.getInStock();
        assertEquals(beforeBuyInStuck, afterBuyInStuck + 1);
        assertEquals(buyList.size(), 1);
        assertEquals(buyList.iterator().next(), commodity);
    }

    @Test(expected = CommodityNotFound.class)
    public void addToBuyList_CommodityNotFound_Throws() throws Exception, CommodityNotFound, UserNotFound, CommodityNotInStuck {
        Integer notInCommodityId = 7;
        BuyModel buyModel = new BuyModel();
        buyModel.commodityId = notInCommodityId;
        buyModel.username = username;
        AddToBuyList command = new AddToBuyList();

        command.handle(buyModel);
    }

    @Test(expected = UserNotFound.class)
    public void addToBuyList_UserNotFound_Throws() throws Exception, UserNotFound, CommodityNotFound, CommodityNotInStuck {
        BuyModel buyModel = new BuyModel();
        buyModel.commodityId = commodityId;
        buyModel.username = username + "NotIn";
        AddToBuyList command = new AddToBuyList();

        command.handle(buyModel);
    }

    @Test(expected = CommodityNotInStuck.class)
    public void addToBuyList_CommodityNotInStuck_Throws() throws Exception, UserNotFound, CommodityNotFound, CommodityNotInStuck {
        Integer zeroInStock = 0;
        Integer notInStockCommodityId = 2;
        DataGenerator.GenerateCommodity(notInStockCommodityId, "Product A", 1, 10.0, new String[]{"Phone"}, 4.0, zeroInStock);

        BuyModel buyModel = new BuyModel();
        buyModel.commodityId = notInStockCommodityId;
        buyModel.username = username;
        AddToBuyList command = new AddToBuyList();

        command.handle(buyModel);
    }
}
