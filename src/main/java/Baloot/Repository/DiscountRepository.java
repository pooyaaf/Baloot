package Baloot.Repository;

import Baloot.Entity.Discount;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount, String> {
}
