package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.Brand;
import paczka.entity.User;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {

    Brand findByOwner(User owner);
}
