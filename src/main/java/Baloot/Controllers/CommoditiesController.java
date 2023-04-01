package Baloot.Controllers;


import Baloot.Commands.GetCommoditiesList;
import Baloot.Context.ContextManager;
import Baloot.Context.Filter.FilterManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import Baloot.View.CommodityListModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/commodities/*")
public class CommoditiesController extends HttpServlet {
    private CommodityListModel getCommoditiesModel() {
        Collection<Commodity> commodities = ContextManager.getInstance().getAllCommodities();
        ArrayList<Commodity> filtered = FilterManager.getInstance().filter(commodities);
        CommodityListModel result = new CommodityListModel();
        result.commoditiesList = new ArrayList<>();
        for (Commodity commodity : filtered) {
            result.commoditiesList.add(commodity.getModel());
        }
        return result;
    }

    private CommodityListModel getSuggestedCommoditiesModel(Commodity commodity) {
        ArrayList<Commodity> suggestedCommodities = ContextManager.getInstance().getSuggestedCommodities(commodity);
        CommodityListModel commodityListModel = new CommodityListModel();
        commodityListModel.commoditiesList = new ArrayList<>();
        for (Commodity suggestedCommodity : suggestedCommodities) {
            commodityListModel.commoditiesList.add(suggestedCommodity.getModel());
        }
        return commodityListModel;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getPathInfo() == null) {
            request.setAttribute("commodities", getCommoditiesModel());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Commodities.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            try {
                Integer commodityId = Integer.valueOf(segments[1]);
                Commodity commodity = ContextManager.getInstance().getCommodity(commodityId);
                request.setAttribute("commodity", commodity.getReportModel());
                request.setAttribute("suggested", getSuggestedCommoditiesModel(commodity));
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Commodity.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                request.setAttribute("err", e.getClass());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/404.jsp");
                requestDispatcher.forward(request, response);
            } catch (CommodityNotFound e) {
                throw new RuntimeException(e);
            }
        }

    }
}