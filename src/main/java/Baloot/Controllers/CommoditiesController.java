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
public class CommoditiesController extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getPathInfo() == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Commodities.jsp");
            requestDispatcher.forward(request, response);
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            try {
                Integer commodityId = Integer.valueOf(segments[1]);
                Commodity commodity = ContextManager.getInstance().getCommodity(commodityId);
                request.setAttribute("commodity", commodity.getReportModel());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Commodity.jsp");
                requestDispatcher.forward(request, response);
            } catch (Exception e) {
                request.setAttribute("err", e.getClass());
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/404.jsp");
                requestDispatcher.forward(request, response);
            } catch (CommodityNotFound e) {
                throw new RuntimeException(e);
            }
        }

    }
}