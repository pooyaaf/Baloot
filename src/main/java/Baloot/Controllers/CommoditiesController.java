package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Context.Filter.FilterManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Exception.CommodityNotFound;
import Baloot.View.CommodityFullModel;
import Baloot.View.CommodityListModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/commodities")
public class CommoditiesController {
    private CommodityListModel getSuggestedCommoditiesModel(Commodity commodity) {
        ArrayList<Commodity> suggestedCommodities = ContextManager.getInstance().getSuggestedCommodities(commodity);
        CommodityListModel commodityListModel = new CommodityListModel();
        commodityListModel.commoditiesList = new ArrayList<>();
        for (Commodity suggestedCommodity : suggestedCommodities) {
            commodityListModel.commoditiesList.add(suggestedCommodity.getModel());
        }
        return commodityListModel;
    }
    @GetMapping
    public CommodityListModel all() {
        Collection<Commodity> commodities = ContextManager.getInstance().getAllCommodities();
        ArrayList<Commodity> filtered = FilterManager.getInstance().filter(commodities);
        CommodityListModel result = new CommodityListModel();
        result.commoditiesList = new ArrayList<>();
        for (Commodity commodity : filtered) {
            result.commoditiesList.add(commodity.getModel());
        }
        return result;
    }

    @GetMapping("/{id}")
    public CommodityFullModel one(@PathVariable Integer id){
        try {
            Commodity commodity = ContextManager.getInstance().getCommodity(id);
            CommodityFullModel commodityFullModel = new CommodityFullModel();
            commodityFullModel.commodityShortModel = commodity.getReportModel();
            commodityFullModel.suggestedCommoditiesModel = getSuggestedCommoditiesModel(commodity);
            return commodityFullModel;
        }
        catch (Exception | CommodityNotFound e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/search")
    public void search(@RequestParam("search") String search_fields, @RequestParam("action") String action) {
        if (action.equals("search_by_category")) {
            FilterManager.getInstance().addSearchByCategory(search_fields);
        } else if (action.equals("search_by_name")) {
            FilterManager.getInstance().addSearchByName(search_fields);
        } else if (action.equals("clear")) {
            FilterManager.getInstance().reset();
        } else if (action.equals("sort_by_rate")) {
            FilterManager.getInstance().setSort();
        }
    }
}