package Baloot.Controllers;

import Baloot.Commands.Payment;
import Baloot.Commands.RemoveFromBuyList;
import Baloot.Context.UserContext;
import Baloot.Entity.User;
import Baloot.Model.CommodityBuyListModel;
import Baloot.Model.UserByIdModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {
    @SneakyThrows
    private void payment(HttpServletResponse response) {
        if (BaseController.isNotAuthenticated(response)) return;
        Payment command = new Payment();
        UserByIdModel model = new UserByIdModel();
        model.user_id = UserContext.username;
        command.handle(model);
        response.sendRedirect("/buyList");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        payment(response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        payment(response);
    }
}
