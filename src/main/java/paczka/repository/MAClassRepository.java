package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.MAClass;
import paczka.entity.MAOffer;
import paczka.entity.User;

import java.util.List;

@Repository
public interface MAClassRepository extends JpaRepository<MAClass,Long> {

    List<MAClass> findAllByMaOffer(MAOffer maOffer);
    List<MAClass> findAllByCoach(User user);
}
