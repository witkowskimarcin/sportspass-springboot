package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.CanceledClass;

@Repository("CanceledClassRepository")
public interface CanceledClassRepository extends JpaRepository<CanceledClass,Long> {
}
