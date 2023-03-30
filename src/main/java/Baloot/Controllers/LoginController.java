package Baloot.Controllers;

import java.io.IOException;

import Baloot.Context.ContextManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Baloot.Context.UserContext;

@WebServlet("/login")
public class LoginController extends  HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("uesrname");
        String password = req.getParameter("Password");
        if (ContextManager.getInstance().isUserPassExist(username, password)) {
            UserContext.username = username;
            resp.sendRedirect("/");
        }
        else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
