package Baloot.Controllers;

import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.UserContext;
import Baloot.Model.CommodityBuyListModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/removeFromBuyList/*")
public class RemoveFromBuyListController extends HttpServlet {
    private static boolean isNotAuthenticated(HttpServletResponse response) throws IOException {
        if (UserContext.username == null) {
            response.sendRedirect("/login");
            return true;
        }
        return false;
    }

    @SneakyThrows
    private void removeFromBuyList(String commodityId, HttpServletResponse response) {
        RemoveFromBuyList removeFromBuyList = new RemoveFromBuyList();
        CommodityBuyListModel model = new CommodityBuyListModel();
        model.username = UserContext.username;
        model.commodityId = commodityId;
        removeFromBuyList.handle(model);
        response.sendRedirect("/buyList");
    }

    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (isNotAuthenticated(response)) return;
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            removeFromBuyList(segments[1], response);
        }
    }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (isNotAuthenticated(response)) return;
        removeFromBuyList(request.getParameter("commodityId"), response);
    }
}
