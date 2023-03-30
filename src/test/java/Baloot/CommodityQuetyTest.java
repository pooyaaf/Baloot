package Baloot;

import Baloot.Commands.GetCommoditiesByPriceRange;
import Baloot.Context.ContextManager;
import Baloot.Model.CommoditiesListByPriceRangeModel;
import Baloot.View.CommodityListModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommodityQuetyTest {
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
    public void getCommodityByPrice_GetAllCommodities_Runs() throws Exception {
        GetCommoditiesByPriceRange getCommoditiesByPriceRange = new GetCommoditiesByPriceRange();
        CommoditiesListByPriceRangeModel model = new CommoditiesListByPriceRangeModel();

        model.start_price = 13.0;
        model.end_price =16.0;
        CommodityListModel output = getCommoditiesByPriceRange.handle(model);


        assertEquals(output.commoditiesList.size(), 1);
    }

    @Test
    public void getCommodityByPrice_GetNoCommodities_Runs() throws Exception {
        GetCommoditiesByPriceRange getCommoditiesByPriceRange = new GetCommoditiesByPriceRange();
        CommoditiesListByPriceRangeModel model = new CommoditiesListByPriceRangeModel();

        model.start_price = 5.0;
        model.end_price = 9.0;
        CommodityListModel output = getCommoditiesByPriceRange.handle(model);

        assertEquals(output.commoditiesList.size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCommodityByPrice_NegativePriceRange_ThrowsException() throws Exception {
        GetCommoditiesByPriceRange getCommoditiesByPriceRange = new GetCommoditiesByPriceRange();
        CommoditiesListByPriceRangeModel model = new CommoditiesListByPriceRangeModel(-10,20);
        getCommoditiesByPriceRange.handle(model);
    }

}
