package Baloot.service;

import Baloot.Entity.Comment;
import Baloot.Entity.Commodity;
import Baloot.Model.CommentReportModel;
import Baloot.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    public void addComment(Comment comment) {
        repository.save(comment);
    }
    public ArrayList<CommentReportModel> getCommentsOfCommodity(Commodity commodity) {

        List<Comment> comments = (List<Comment>) repository.findAllByCommodity(commodity);
        return new ArrayList<>(comments.stream().map(o -> o.getReportModel()).toList());
    }

    public Optional<Comment> getCommentById(Integer id) {
        return repository.findById(id);
    }
}
