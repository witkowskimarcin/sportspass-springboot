package paczka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paczka.entity.MAPass;
import paczka.entity.User;

import java.util.List;

@Repository
public interface MAPassRepository extends JpaRepository<MAPass,Long> {

    //List<MAPass> findAllByUser(User user);
}