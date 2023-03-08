package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Category;
import Baloot.Entity.Commodity;
import Baloot.Exception.CategoryNotFound;
import Baloot.Exception.UserNotFound;
import Baloot.Model.CategoryModel;
import Baloot.Model.CommoditiesListByCategoryModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;

@Route("getCommoditiesByCategory/{categories}")
public class GetCommoditiesByCategory extends Command {
    @AcceptMethod(RequestMethod.GET)
    public CommoditiesListByCategoryModel handle(CategoryModel input) {
        CommoditiesListByCategoryModel result = new CommoditiesListByCategoryModel();
        result.commoditiesListByCategory = new ArrayList<>();
        try {
            Category category = ContextManager.getCategory(input.category);
            Collection<Commodity> commodities = category.getAllCommodity();
            for (Commodity commodity : commodities) {
                result.commoditiesListByCategory.add(commodity.getReportModel());
            }
            return result;
        }
        catch (CategoryNotFound e) {
            return result;
        }
    }
}
