package Baloot.Controllers;

import Baloot.Commands.GetBuyList;
import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Exception.UserNotFound;
import Baloot.Model.BuyListModel;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Model.UserByUsernameModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/buyList")
public class BuyListController extends HttpServlet {

    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserByUsernameModel model = new UserByUsernameModel();
        model.username = UserContext.username;
        GetBuyList command = new GetBuyList();
        BuyListModel buylist = command.handle(model);


        request.setAttribute("buylist", buylist);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/BuyList.jsp");
        requestDispatcher.forward(request, response);
    }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Integer commodityId = Integer.valueOf(request.getParameter("commodity_id"));
        if(action.equals("remove"))
        {
            CommodityBuyListModel model = new CommodityBuyListModel();
            model.username = UserContext.username;
            model.commodityId = String.valueOf(commodityId);
            RemoveFromBuyList command = new RemoveFromBuyList();
            command.handle(model);
            response.sendRedirect("/BuyList.jsp");
        }
    }

}
