package fr.cenotelie.training.movies.controllers;


import fr.cenotelie.training.movies.dao.MovieDAO;
import fr.cenotelie.training.movies.model.Movie;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/movies")
@AllArgsConstructor
public class MovieController {

    private MovieDAO movieDAO;

    @PostMapping
    public ResponseEntity<?> createMovie(@Valid @RequestBody final Movie movie) {
        final var createdMovie = movieDAO.save(movie);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdMovie.getId()).toUri()).body(createdMovie);
    }

    @GetMapping
    public ResponseEntity<?> findAllMovie() {
        final var movies = movieDAO.findAll();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(movies);
    }

    @GetMapping("/{criteria}")
    public ResponseEntity<EntityModel<?>> findMovie(@PathVariable final String criteria) {
        Optional<Movie> opt;
        try {
            opt = movieDAO.findById(Long.valueOf(criteria));
        } catch (final NumberFormatException nfe) {
            opt = movieDAO.findDistinctByTitle(criteria);
        }

        if (opt.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        final WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAllMovie());
        final var entity = EntityModel.of(opt.get());
        entity.add(link.withRel("find_all_movie"));
        return ResponseEntity.ok(entity);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable final Long id) {
        movieDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<?> updateMovie(@RequestBody final Movie movie) {
        return ResponseEntity.ok(movieDAO.save(movie));
    }
}
