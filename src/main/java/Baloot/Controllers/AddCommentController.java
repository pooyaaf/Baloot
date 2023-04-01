package Baloot.Controllers;

import Baloot.Commands.AddComment;
import Baloot.Context.ContextManager;
import Baloot.Context.UserContext;
import Baloot.Model.CommentModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@WebServlet("/addComment/*")
public class AddCommentController extends HttpServlet {
    @SneakyThrows
    public void addComment(HttpServletRequest request, HttpServletResponse response) {
        if (BaseController.isNotAuthenticated(response)) return;
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            AddComment command = new AddComment();
            CommentModel commentModel = new CommentModel();
            commentModel.user = ContextManager.getInstance().getUser(UserContext.username);
            commentModel.commodityId = Integer.valueOf(segments[1]);
            commentModel.text = request.getParameter("comment");
            LocalDateTime localDateTime = LocalDateTime.now();
            commentModel.date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            command.handle(commentModel);
            response.sendRedirect("/commodities/" + commentModel.commodityId);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        addComment(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        addComment(request, response);
    }
}
