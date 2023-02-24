package Baloot;

import Baloot.Commands.GetCommoditiesByCategory;
import Baloot.Model.*;
import org.junit.Before;
import org.junit.Test;

import static Baloot.DataGenerator.generateReportCommodityModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class GetCommoditiesByCategoryTest {
    private Integer commodityId = 1;
    private String username = "user1";
    private String category = "Phone";
    private Integer inStuck = 100;

    @Before
    public void setUp() throws Exception {
        // Generate users
        DataGenerator.GenerateUser("user1", "password1", "user1@example.com", "1990-01-01", "123 Main St", 1000);
        // Generate providers
        DataGenerator.GenerateProvider(1, "Provider A", "2023-02-23");
        // Generate commodities
        DataGenerator.GenerateCommodity(1, "Product A", 1, 10.0, "[Phone]", 4.0, inStuck);
    }

    @Test
    public void getCommoditiesByCategory_ShouldSuccessful() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.category = category;
        ReportCommodityModel expectedModel = generateReportCommodityModel(1, "Product A", 1, 10.0, "[Phone]", 4.0);
        GetCommoditiesByCategory command = new GetCommoditiesByCategory();


        CommoditiesListByCategoryModel commoditiesListByCategoryModel = command.handle(categoryModel);
        assertEquals(1, commoditiesListByCategoryModel.commoditiesListByCategory.size());
        assertThat(commoditiesListByCategoryModel.commoditiesListByCategory.get(0))
                .usingRecursiveComparison()
                .isEqualTo(expectedModel);
    }

    @Test
    public void getCommoditiesByCategory_ifCategoryNotExistOutputDataShouldEmpty() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.category = category + "notIn";
        GetCommoditiesByCategory command = new GetCommoditiesByCategory();

        CommoditiesListByCategoryModel commoditiesListByCategoryModel = command.handle(categoryModel);
        assertEquals(0, commoditiesListByCategoryModel.commoditiesListByCategory.size());
    }
}
