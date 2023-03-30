package Baloot.Controllers;

import Baloot.Commands.AddCredit;
import Baloot.Context.UserContext;
import Baloot.Model.AddCreditModel;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/credit")
public class CreditController extends HttpServlet {
    @SneakyThrows
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserContext.username == null) {
            response.sendRedirect("/login");
            return;
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Credit.jsp");
        requestDispatcher.forward(request, response);
    }

    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (UserContext.username == null) {
            response.sendRedirect("/login");
            return;
        }
        AddCreditModel addCreditModel = new AddCreditModel();
        addCreditModel.user_id = UserContext.username;
        addCreditModel.credit = request.getParameter("credit");
        AddCredit command = new AddCredit();
        command.handle(addCreditModel);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Credit.jsp");
        requestDispatcher.forward(request, response);
    }

}
