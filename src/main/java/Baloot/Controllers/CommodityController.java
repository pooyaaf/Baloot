package Baloot.Controllers;

import Baloot.Context.ContextManager;
import Baloot.Entity.Commodity;
import Baloot.Exception.CommodityNotFound;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CommodityController  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer commodityId = Integer.valueOf(req.getParameter("commodity_id"));
        try{
            Commodity commodity = ContextManager.getInstance().getCommodity(commodityId);
            req.setAttribute("commodity", commodity.getModel());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("commodity.jsp");
            requestDispatcher.forward(req, resp);
        }
        catch(Exception e){
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/404.jsp");
            requestDispatcher.forward(req, resp);
        } catch (CommodityNotFound e) {
            throw new RuntimeException(e);
        }
    }
}
