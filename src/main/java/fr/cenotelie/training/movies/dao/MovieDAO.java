package fr.cenotelie.training.movies.dao;


import fr.cenotelie.training.movies.model.Movie;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MovieDAO extends JpaRepository<Movie, Long> {

    Optional<Movie> findDistinctByTitle(String title);

    List<Movie> findDistinctByProductionDateBetween(LocalDate before, LocalDate after);

}
