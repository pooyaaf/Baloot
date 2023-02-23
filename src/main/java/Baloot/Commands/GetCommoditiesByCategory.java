package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Category;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CategoryNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CategoryModel;
import Baloot.Model.ReportCommodityListModel;
import Baloot.Model.UserByUsernameModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.HashSet;

@Route("getCommoditiesByCategory")
public class GetCommoditiesByCategory extends Command {
    @AcceptMethod(RequestMethod.GET)
    public ReportCommodityListModel handle(CategoryModel input) throws Exception, UserNotFound, CategoryNotFound {
        Category category = ContextManager.getCategory(input.category);
        HashSet<Commodity> commodities = category.getAllCommodity();
        ReportCommodityListModel result = new ReportCommodityListModel();
        result.ReportCommodityList = new ArrayList<>();
        for (Commodity commodity : commodities) {
            result.ReportCommodityList.add(commodity.getReportModel());
        }
        return result;
    }
}
