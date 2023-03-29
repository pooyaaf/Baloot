package Baloot.Controllers;


import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/commodities/*")
public class CommoditiesController extends BaseController {
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getPathInfo() == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Commodities.jsp");
            requestDispatcher.forward(req, resp);
            return;
        }
        String[] segments = req.getPathInfo().split("/");
        if (segments.length == 2) {
            try {
                Integer commodityId = Integer.valueOf(segments[1]);
                Commodity commodity = ContextManager.getInstance().getCommodity(commodityId);
                req.setAttribute("commodity", commodity.getModel());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/Commodity.jsp");
                requestDispatcher.forward(req, resp);
            } catch (Exception e) {
                req.setAttribute("err", e.getClass());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/404.jsp");
                requestDispatcher.forward(req, resp);
            } catch (CommodityNotFound e) {
                req.setAttribute("err", e.getClass());
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("/404.jsp");
                requestDispatcher.forward(req, resp);
            }
        }

    }
}
