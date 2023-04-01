package Baloot.Controllers;

import Baloot.Commands.GetCommoditiesByCategory;
import Baloot.Commands.GetCommoditiesList;
import Baloot.Context.ContextManager;
import Baloot.Context.Filter.FilterManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Commodity;
import Baloot.Entity.User;
import Baloot.Model.CategoryModel;
import Baloot.View.CommodityListModel;
import jakarta.servlet.Filter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/commodities/search")
public class CommoditiesSearchController extends HttpServlet {
    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String search = request.getParameter("search");
        String action = request.getParameter("action");
        if (action.equals("search_by_category")) {
            FilterManager.getInstance().addSearchByCategory(search);
        } else if (action.equals("search_by_name")) {
            FilterManager.getInstance().addSearchByName(search);
        } else if (action.equals("clear")) {
            FilterManager.getInstance().reset();
        } else if (action.equals("sort_by_rate")) {
            FilterManager.getInstance().setSort();
        }
        response.sendRedirect("/commodities");
    }
}
