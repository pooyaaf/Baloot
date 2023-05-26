package Baloot.service;

import Baloot.Entity.Comment;
import Baloot.Model.CommentReportModel;
import Baloot.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    public void addComment(Comment comment) {
        repository.save(comment);
    }
    public ArrayList<CommentReportModel> getCommentsOfCommodity(Integer commodityId){
//        List<Comment> comments = (List<Comment>) repository.findAllByCommodityId(commodityId);
//        return new ArrayList<>(comments.stream().map(o -> o.getReportModel()).toList());
        return new ArrayList<>();
    }
}
