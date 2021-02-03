package example.dealer.repos;

import example.dealer.entities.Order;
import example.dealer.entities.Prototype;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends CrudRepository<Order, Long> {
    List<Order> findByPrototype(Optional<Prototype> prototype);
}
