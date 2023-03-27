package Baloot.Commands;

import Baloot.AcceptMethod;
import Baloot.Context.ContextManager;
import Baloot.Entity.Category;
import Baloot.Entity.Commodity;
import Baloot.Exception.CategoryNotFound;
import Baloot.Model.CategoryModel;
import Baloot.Model.CommoditiesListByCategoryModel;
import Baloot.Model.view.CommodityListModel;
import Baloot.RequestMethod;
import Baloot.Route;

import java.util.ArrayList;
import java.util.Collection;

@Route("commodities/search/{categories}")
public class GetCommoditiesByCategory extends Command {
    @AcceptMethod(RequestMethod.GET)
    public CommodityListModel handle(CategoryModel input) {
        CommodityListModel result = new CommodityListModel();
        result.commoditiesList = new ArrayList<>();
        try {
            Category category = ContextManager.getInstance().getCategory(input.categories);
            Collection<Commodity> commodities = category.getAllCommodity();
            for (Commodity commodity : commodities) {
                result.commoditiesList.add(commodity.getModel());
            }
            return result;
        }
        catch (CategoryNotFound e) {
            return result;
        }
    }
}
