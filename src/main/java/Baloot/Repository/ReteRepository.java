package Baloot.Repository;

import Baloot.Entity.Comment;
import Baloot.Entity.Commodity;
import Baloot.Entity.Rate;
import Baloot.Entity.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReteRepository extends CrudRepository<Rate, Integer> {
    Iterable<Rate> findAllByCommodity(Commodity commodity);
}
