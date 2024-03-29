package Baloot.Repository;

import Baloot.Entity.Comment;
import Baloot.Entity.Vote;
import Baloot.Entity.VoteId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, VoteId> {
    Iterable<Vote> findAllByVoteID_Comment(Comment comment);
}
