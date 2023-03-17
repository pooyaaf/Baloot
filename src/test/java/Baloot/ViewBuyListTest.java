package Baloot;

import Baloot.Commands.AddCommodityToBuyList;
import Baloot.Commands.GetBuyList;
import Baloot.Commands.console.AddToBuyList;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.CommodityNotInStuck;
import Baloot.Exception.UserNotFound;
import Baloot.Model.*;
import Baloot.Model.view.CommodityShortModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ViewBuyListTest {
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
    public void GetBuyList_ShouldSuccessful() throws CommodityNotFound, Exception, UserNotFound, CommodityNotInStuck {
        CommodityBuyListModel commodityBuyListModel = new CommodityBuyListModel();
        commodityBuyListModel.commodityId = commodityId.toString();
        commodityBuyListModel.username = username;
        UserByUsernameModel userByUsernameModel = new UserByUsernameModel();
        userByUsernameModel.username = username;

        AddCommodityToBuyList command = new AddCommodityToBuyList();
        GetBuyList buyListCommand = new GetBuyList();

        command.handle(commodityBuyListModel);
        BuyListModel buyListModel = buyListCommand.handle(userByUsernameModel);
        assertEquals(1, buyListModel.buyList.size());
    }

    @Test(expected = UserNotFound.class)
    public void GetBuyList_UserNotFound_Throws() throws Exception, UserNotFound, CommodityNotFound, CommodityNotInStuck {
        UserByUsernameModel userByUsernameModel = new UserByUsernameModel();
        userByUsernameModel.username = username + "NotIn";

        GetBuyList buyListCommand = new GetBuyList();
        buyListCommand.handle(userByUsernameModel);
    }
}
