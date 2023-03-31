package Baloot.Controllers;

import Baloot.Commands.RateCommodity;
import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Model.RateModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/rateCommodity/*")
public class RateCommodityController extends HttpServlet {
    @SneakyThrows
    private static boolean isNotAuthenticated(HttpServletResponse response) {
        if (UserContext.username == null) {
            response.sendRedirect("/login");
            return true;
        }
        return false;
    }

    @SneakyThrows
    private void rateCommodity(Integer commodityId, Integer rate, HttpServletResponse response) {
        if (isNotAuthenticated(response)) return;
        RateCommodity command = new RateCommodity();
        RateModel model = new RateModel();
        model.rate = rate;
        model.commodityId = commodityId;
        model.username = UserContext.username;
        command.handle(model);
        response.sendRedirect("/commodities/" + model.commodityId);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 3) {
            rateCommodity(Integer.valueOf(segments[1]), Integer.valueOf(segments[2]), response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            rateCommodity(Integer.valueOf(segments[1]), Integer.valueOf(request.getParameter("quantity")), response);
        }
    }
}
