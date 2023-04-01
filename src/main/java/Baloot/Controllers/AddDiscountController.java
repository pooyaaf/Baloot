package Baloot.Controllers;

import Baloot.Commands.AddComment;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Entity.Discount;
import Baloot.Entity.User;
import Baloot.Model.CommentModel;
import Baloot.Model.DiscountModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@WebServlet("/addDiscount/*")
public class AddDiscountController extends HttpServlet {
    @SneakyThrows
    public void addDiscount(HttpServletRequest request, HttpServletResponse response) {
        if (BaseController.isNotAuthenticated(response)) return;
        User user = ContextManager.getInstance().getUser(UserContext.username);

        Discount discount = ContextManager.getInstance().getDiscount(request.getParameter("discount"));
        user.addDiscount(discount);
        response.sendRedirect("/buyList");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        addDiscount(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        addDiscount(request, response);
    }
}
