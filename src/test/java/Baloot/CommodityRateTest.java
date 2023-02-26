package Baloot;

import Baloot.Commands.RateCommodity;
import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.InvalidRateScore;
import Baloot.Exception.UserNotFound;
import Baloot.Model.RateModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommodityRateTest {
    private String username = "user1";
    private Integer commodityId = 1;
    @Before
    public void setUp() throws Exception {
        // Generate users
        DataGenerator.GenerateUser("user1", "password1", "user1@example.com", "1990-01-01", "123 Main St", 1000);
        DataGenerator.GenerateUser("user2", "password2", "user2@example.com", "1995-05-05", "456 Elm St", 500);
        DataGenerator.GenerateUser("user3", "password3", "user3@example.com", "2000-12-31", "789 Oak St", 250);
        // Generate providers
        DataGenerator.GenerateProvider(1, "Provider A", "2023-02-23");
        DataGenerator.GenerateProvider(2, "Provider B", "2023-02-23");
        // Generate commodities
        DataGenerator.GenerateCommodity(1, "Product A", 1, 10.0, new String[]{"Phone"}, 4.0, 100);
        DataGenerator.GenerateCommodity(2, "Product B", 1, 20.0, new String[]{"Technology,Phone"}, 3.5, 50);
        DataGenerator.GenerateCommodity(3, "Product C", 2, 15.0, new String[]{"Book"}, 4.5, 200);
    }
    @After
    public void teardown() {
        ContextManager.resetContext();
    }
    @Test
    public void rateCommodity_SuccessfulRating() throws UserNotFound, CommodityNotFound, Exception {
        RateModel model = new RateModel();
        model.commodityId = commodityId;
        model.score = 8;
        model.username = username;

        RateCommodity command = new RateCommodity();

        command.handle(model);

        Commodity commodity = ContextManager.getCommodity(commodityId);
//        User user = ContextManager.getUser(username);
        Double rating = 8.0;
        assertEquals(rating,commodity.getRate());
    }

    @Test(expected = InvalidRateScore.class)
    public void rateCommodity_RateOutOfRangeLow_Throws() throws Exception, UserNotFound, CommodityNotFound {
        RateModel model = new RateModel();
        model.commodityId = commodityId;
        model.score = -1;
        model.username = username;
        RateCommodity command = new RateCommodity();

        command.handle(model);
    }
    @Test(expected = InvalidRateScore.class)
    public void rateCommodity_RateOutOfRangeHigh_Throws() throws Exception, UserNotFound, CommodityNotFound {
        RateModel model = new RateModel();
        model.commodityId = commodityId;
        model.score = 11;
        model.username = username;
        RateCommodity command = new RateCommodity();

        command.handle(model);
    }
    @Test(expected = CommodityNotFound.class)
    public void rateCommodity_CommodityNotFound_Throws() throws Exception, UserNotFound, CommodityNotFound {
        RateModel model = new RateModel();
        model.commodityId = commodityId+100;
        model.score = 8;
        model.username = username;
        RateCommodity command = new RateCommodity();

        command.handle(model);
    }
    @Test(expected = UserNotFound.class)
    public void rateCommodity_UserNotFound_Throws() throws Exception, UserNotFound, CommodityNotFound {
        RateModel model = new RateModel();
        model.commodityId = commodityId;
        model.score = 8;
        model.username = username+"NotIn";
        RateCommodity command = new RateCommodity();

        command.handle(model);
    }
}
