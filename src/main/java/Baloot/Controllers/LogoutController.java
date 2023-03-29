package Baloot.Controllers;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Baloot.Context.UserContext;

@WebServlet("/logout")
public class LogoutController extends BaseController{
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserContext.username = null;
        resp.sendRedirect("/login");
    }
}
