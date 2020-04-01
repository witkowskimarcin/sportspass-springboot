package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.GymOfferDetail;

@Repository
public interface GymOfferDetailRepository extends JpaRepository<GymOfferDetail,Long> {

}
