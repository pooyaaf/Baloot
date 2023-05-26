package Baloot.Repository;

import Baloot.Entity.Comment;
import Baloot.Entity.Commodity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    Iterable<Comment> findAllByCommodity(Commodity commodity);
}
