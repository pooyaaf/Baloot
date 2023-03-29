package Baloot.Controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Baloot.Context.UserContext;

public abstract class BaseController extends HttpServlet {
    void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect("/Baloot_war_exploded/home");
    };

    void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect("/Baloot_war_exploded/home");
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UserContext.username == null) {
            resp.sendRedirect("/Baloot_war_exploded/login");
        }
        try {
            get(req, resp);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (UserContext.username == null) {
            resp.sendRedirect("/Baloot_war_exploded/login");
        }
        try {

            post(req, resp);

        } catch (Exception e) {
        }
    }
}