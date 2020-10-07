package com.microservices.movieinfoservice.resources;

import com.microservices.movieinfoservice.models.Movie;
import com.microservices.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    /**
     * Gets the MovieDB API key from app.properties
     */
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Gets a MovieSummary info from a movie API.
     *
     * @param movieID
     * @return Movie
     */
    @RequestMapping("/{movieID}")
    public Movie getMovieInfo(@PathVariable("movieID") String movieID) {
        MovieSummary movieSummary = restTemplate.getForObject(
                "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=" + apiKey,
                MovieSummary.class
        );
        return new Movie(movieID, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
