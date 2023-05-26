package Baloot.Repository;

import Baloot.Entity.Discount;
import Baloot.Entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount, String> {
}
