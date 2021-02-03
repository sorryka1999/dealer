package example.dealer.repos;

import example.dealer.entities.Brand;
import example.dealer.entities.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepo extends CrudRepository<Car, Long> {
    List<Car> findByBrand(Optional<Brand> brand);
    List<Car> findByName(String name);

    List<Car> findByBrandAndActive(Optional<Brand> brand, Boolean active);
}
