package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.MAOfferDetail;

@Repository
public interface MAOfferDetailRepository extends JpaRepository<MAOfferDetail,Long> {
}
