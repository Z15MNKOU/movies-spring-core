package fr.cenotelie.training.movies.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.cenotelie.training.movies.constraints.FirstLetterUppderConstraint;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movie", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"title", "director"})
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Column(nullable = false)
    @FirstLetterUppderConstraint
    private String title;
    @Column(nullable = false)
    @NotBlank
    private String director;
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Genre genre;
    @Column(nullable = false)
    @Past
    private LocalDate productionDate;
    @Column(nullable = false)
    @Past
    private LocalDate releaseDate;
    @Column(columnDefinition = "TEXT") //Specific to postgresql
    private String description;
    @Min(0)
    @Max(5)
    private Double rating;
    private String fromBook;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Acting",
            joinColumns = @JoinColumn(name = "actor_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @Valid
    private List<Actor> actors;
}
