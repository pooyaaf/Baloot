package Baloot.Repository;

import Baloot.Entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
//    Iterable<Comment> findAllByCommodityId(Integer commodityId);
}
