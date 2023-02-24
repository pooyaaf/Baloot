package Baloot;

import Baloot.Commands.GetCommodityById;
import Baloot.Exception.CommodityNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CommodityByIdModel;
import Baloot.Model.ReportCommodityModel;
import org.junit.Before;
import org.junit.Test;

import static Baloot.DataGenerator.generateReportCommodityModel;
import static org.assertj.core.api.Assertions.assertThat;

public class GetCommodityByIdTest {
    private Integer commodityId = 1;

    @Before
    public void setUp() throws Exception {
        // Generate providers
        DataGenerator.GenerateProvider(1, "Provider A", "2023-02-23");
        // Generate commodities
        DataGenerator.GenerateCommodity(1, "Product A", 1, 10.0, "[Phone]", 4.0, 100);
    }

    @Test
    public void getCommodityById_ShouldReturnExpectedCommodityInfo() throws CommodityNotFound, Exception {
        ReportCommodityModel expectedModel = generateReportCommodityModel(1, "Product A", 1, 10.0, "[Phone]", 4.0);

        CommodityByIdModel inputModel = new CommodityByIdModel();
        inputModel.id = commodityId;

        GetCommodityById command = new GetCommodityById();

        ReportCommodityModel model = command.handle(inputModel);

        assertThat(model)
                .usingRecursiveComparison()
                .isEqualTo(expectedModel);
    }

    @Test(expected = CommodityNotFound.class)
    public void getCommodityById_IfIdNotAvailableCommodityNotFound_Throws() throws Exception, CommodityNotFound {
        ReportCommodityModel expectedModel = generateReportCommodityModel(1, "Product A", 1, 10.0, "[Phone]", 4.0);

        CommodityByIdModel inputModel = new CommodityByIdModel();
        Integer notAvailableCommodityId = 7;
        inputModel.id = notAvailableCommodityId;

        GetCommodityById command = new GetCommodityById();

        command.handle(inputModel);
    }
}
