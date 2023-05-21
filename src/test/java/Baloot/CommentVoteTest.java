//package Baloot;
//
//import Baloot.Commands.VoteComment;
//import Baloot.Context.ContextManager;
//import Baloot.Exception.CommentNotFound;
//import Baloot.Exception.CommodityNotFound;
//import Baloot.Exception.UserNotFound;
//import Baloot.View.CommodityShortModel;
//import Baloot.View.VoteCommentModel;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//public class CommentVoteTest {
//    private String username = "user1";
//    private Integer commodityId = 1;
//    private Integer commentId;
//    @Before
//    public void setUp() throws Exception {
//        // Generate users
//        DataGenerator.GenerateUser("user1", "password1", "user1@example.com", "1990-01-01", "123 Main St", 1000);
//        // Generate providers
//        DataGenerator.GenerateProvider(1, "Provider A", "2023-02-23");
//        // Generate commodities
//        DataGenerator.GenerateCommodity(1, "Product A", 1, 10.0, new String[]{"Phone"}, 4.0, 100);
//        // Generate comments
//        commentId = DataGenerator.GenerateComment(1, "user1", "text");
//    }
//    @After
//    public void teardown() {
//        ContextManager.resetContext();
//    }
//
//    @Test
//    public void voteComment_LikeSuccessfulRating() throws UserNotFound, CommodityNotFound, CommentNotFound, Exception {
//        VoteCommentModel voteCommentModel = new VoteCommentModel();
//        voteCommentModel.vote = 1;
//        voteCommentModel.commentId = commentId;
//        voteCommentModel.username = "user1";
//        VoteComment command = new VoteComment();
//        CommodityShortModel commodityShortModel = command.handle(voteCommentModel);
//        assertEquals(Integer.valueOf(1), commodityShortModel.commentsList.get(0).like);
//    }
//
//    @Test
//    public void voteComment_DislikeSuccessfulRating() throws UserNotFound, CommodityNotFound, CommentNotFound, Exception {
//        VoteCommentModel voteCommentModel = new VoteCommentModel();
//        voteCommentModel.vote = -1;
//        voteCommentModel.commentId = commentId;
//        voteCommentModel.username = "user1";
//        VoteComment command = new VoteComment();
//        CommodityShortModel commodityShortModel = command.handle(voteCommentModel);
//        assertEquals(Integer.valueOf(1), commodityShortModel.commentsList.get(0).dislike);
//    }
//
//    @Test(expected = CommentNotFound.class)
//    public void voteComment_CommentNotFound_Throws() throws Exception, UserNotFound, CommodityNotFound, CommentNotFound {
//        VoteCommentModel voteCommentModel = new VoteCommentModel();
//        voteCommentModel.vote = 0;
//        voteCommentModel.commentId = -1;
//        voteCommentModel.username = "user1";
//        VoteComment command = new VoteComment();
//        command.handle(voteCommentModel);
//    }
//
//    @Test
//    public void voteComment_ZeroVoteSuccessfulRating() throws UserNotFound, CommodityNotFound, CommentNotFound, Exception {
//        VoteCommentModel voteCommentModel = new VoteCommentModel();
//        voteCommentModel.vote = 0;
//        voteCommentModel.commentId = commentId;
//        voteCommentModel.username = "user1";
//        VoteComment command = new VoteComment();
//        CommodityShortModel commodityShortModel = command.handle(voteCommentModel);
//        assertEquals(Integer.valueOf(0), commodityShortModel.commentsList.get(0).like);
//        assertEquals(Integer.valueOf(0), commodityShortModel.commentsList.get(0).dislike);
//    }
//}
