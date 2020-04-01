package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.GymPass;

@Repository
public interface GymPassRepository extends JpaRepository<GymPass,Long> {
}
