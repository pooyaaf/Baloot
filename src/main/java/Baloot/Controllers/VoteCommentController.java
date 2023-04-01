package Baloot.Controllers;

import Baloot.Commands.RateCommodity;
import Baloot.Commands.VoteComment;
import Baloot.Context.UserContext;
import Baloot.Model.CommentReportModel;
import Baloot.Model.RateModel;
import Baloot.View.CommodityShortModel;
import Baloot.View.VoteCommentModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

@WebServlet("/voteComment/*")
public class VoteCommentController extends HttpServlet {
    @SneakyThrows
    private void voteComment(Integer commentId, Integer vote, HttpServletResponse response) {
        if (BaseController.isNotAuthenticated(response)) return;
        VoteComment command = new VoteComment();
        VoteCommentModel model = new VoteCommentModel();
        model.vote = vote;
        model.commentId = commentId;
        model.username = UserContext.username;
        CommodityShortModel reportModel = command.handle(model);
        response.sendRedirect("/commodities/" + reportModel.commodityModel.id);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 3) {
            voteComment(Integer.valueOf(segments[1]), Integer.valueOf(segments[2]), response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() == null) {
            return;
        }
        String[] segments = request.getPathInfo().split("/");
        if (segments.length == 2) {
            voteComment(Integer.valueOf(segments[1]), Integer.valueOf(request.getParameter("comment_id")), response);
        }
    }
}
