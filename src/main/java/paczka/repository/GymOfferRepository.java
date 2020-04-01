package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.Brand;
import paczka.entity.GymOffer;

import java.util.List;

@Repository
public interface GymOfferRepository extends JpaRepository<GymOffer,Long> {

    List<GymOffer> findAllByLocation(String location);
    List<GymOffer> findAllByBrand(Brand brand);
}
