package com.microservices.moviecatalogueservice.resources;

import com.microservices.moviecatalogueservice.models.CatalogueItem;
import com.microservices.moviecatalogueservice.models.Movie;
import com.microservices.moviecatalogueservice.models.Rating;
import com.microservices.moviecatalogueservice.models.UserRating;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogue")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

//    @Autowired
//    private DiscoveryClient discoveryClient; // For advanced control over load balancing

    @RequestMapping("/{userID}")
    public List<CatalogueItem> getCatalogue(@PathVariable("userID") String userID) {

        // Get all ratings from ratings API | Specifying the Eureka service name
        UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userID, UserRating.class);

        return ratings.getUserRatings().stream().map(rating -> {
            // For each movie ID, call the movie info API and get the details
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieID(), Movie.class);

            // Then put them together
            return new CatalogueItem(movie.getName(), "test", rating.getRating());
        }).collect(Collectors.toList());
    }
}

/* Equivalent of restTemplate... but async/reactive if .block() is taken off
Movie movie = webClientBuilder.build()
        .get() // HTTP method
        .uri("http://localhost:8081/movies/" + rating.getMovieID())
        .retrieve() // fetch the data
        .bodyToMono(Movie.class) // Whatever body is retrieved, convert it to a Movie class (mono = async)
        .block(); // returns the Movie inside the Mono "Mono<Movie>" synchronously
 */