package Baloot.Controllers;

import Baloot.Commands.AddCommodityToBuyList;
import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.UserContext;
import Baloot.Model.CommodityBuyListModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet("/addToBuyList/*")
public class AddToBuyListController extends HttpServlet {
    @SneakyThrows
    public void addToBuyList(HttpServletRequest request, HttpServletResponse response) {
        if (BaseController.isNotAuthenticated(response)) return;
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            AddCommodityToBuyList command = new AddCommodityToBuyList();
            CommodityBuyListModel model = new CommodityBuyListModel();
            model.commodityId = segments[1];
            model.username = UserContext.username;
            command.handle(model);
            response.sendRedirect("/commodities/" + model.commodityId);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        addToBuyList(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        addToBuyList(request, response);
    }
}
