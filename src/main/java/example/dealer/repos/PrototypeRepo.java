package example.dealer.repos;

import example.dealer.entities.Car;
import example.dealer.entities.Prototype;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PrototypeRepo extends CrudRepository<Prototype, Long> {
    List<Prototype> findByCar(Optional<Car> car);
    List<Prototype> findByName(String name);

    List<Prototype> findByCarAndActive(Optional<Car> car, Boolean active);
}
