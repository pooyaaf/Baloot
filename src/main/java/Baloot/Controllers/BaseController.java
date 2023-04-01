package Baloot.Controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Baloot.Context.UserContext;
import lombok.SneakyThrows;

public abstract class BaseController extends HttpServlet {
    @SneakyThrows
    public static boolean isNotAuthenticated(HttpServletResponse response) {
        if (UserContext.username == null) {
            response.sendRedirect("/login");
            return true;
        }
        return false;
    }
    void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect("/");
    };

    void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect("/");
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UserContext.username == null) {
            resp.sendRedirect("/login");
        }
        try {
            get(req, resp);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UserContext.username == null) {
            resp.sendRedirect("/login");
        }
        try {

            post(req, resp);

        } catch (Exception e) {
        }
    }
}