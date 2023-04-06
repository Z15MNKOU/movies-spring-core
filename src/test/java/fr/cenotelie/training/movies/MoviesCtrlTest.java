package fr.cenotelie.training.movies;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cenotelie.training.movies.dao.MovieDAO;
import fr.cenotelie.training.movies.model.Genre;
import fr.cenotelie.training.movies.model.Movie;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MoviesCtrlTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieDAO movieDAO;


    private Movie movie;


    @BeforeEach
    public void init() {
        movie = Movie.builder()
                .title("Les dents de la mer 2")
                .director("Steven Spielberg")
                .genre(Genre.Action)
                .productionDate(LocalDate.of(1977, 3, 29))
                .releaseDate(LocalDate.of(1978, 3, 17))
                .description("Very good movie")
                .rating(4.7)
                .fromBook(null)
                .actors(List.of())
                .build();
        when(movieDAO.findAll()).thenReturn(List.of(movie));
    }

    @Test
    void testMovieCreation() throws Exception {
        when(movieDAO.save(Mockito.any(Movie.class))).thenReturn(movie);
        final ResultActions response =
                mockMvc.perform(post("/api/v1/movies").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movie)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(movie.getTitle())));
    }

    @Test
    void shouldFindAllMovie() throws Exception {
        movie.setId(1L);
        when(movieDAO.findAll()).thenReturn(List.of(movie));
        mockMvc.perform(get("/api/v1/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(movie.getTitle())));
    }
}
