package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.*;

import java.util.List;

@Repository
public interface MAOfferRepository extends JpaRepository<MAOffer,Long> {

    List<MAOffer> findAllByLocation(String location);
    List<MAOffer> findAllByBrand(Brand brand);
}
