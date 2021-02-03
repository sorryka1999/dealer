package example.dealer.repos;

import example.dealer.entities.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BrandRepo extends CrudRepository<Brand, Long> {
    List<Brand> findByName(String name);

    List<Brand> findByActive(Boolean active);
}
