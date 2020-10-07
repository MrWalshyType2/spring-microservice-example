package com.microservices.moviecatalogueservice.resources;

import com.microservices.moviecatalogueservice.models.CatalogueItem;
import com.microservices.moviecatalogueservice.models.Movie;
import com.microservices.moviecatalogueservice.models.Rating;
import com.microservices.moviecatalogueservice.models.UserRating;
import com.microservices.moviecatalogueservice.services.MovieInfo;
import com.microservices.moviecatalogueservice.services.UserRatingInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

//    @Autowired
//    private DiscoveryClient discoveryClient; // For advanced control over load balancing

    /**
     * Gets a catalogue of Movie information, has a fallback method of
     * getFallbackCatalogue. Circuit breaker is provided by Hystrix.
     *
     * @param userID
     * @return List<CatalogueItem>
     */
    @RequestMapping("/{userID}")
    //@HystrixCommand(fallbackMethod = "getFallbackCatalogue") // Indicates to Hystrix that this method should 'break the circuit' if it goes down
    public List<CatalogueItem> getCatalogue(@PathVariable("userID") String userID) {
        // Get all ratings from ratings API | Specifying the Eureka service name
        UserRating ratings = userRatingInfo.getUserRating(userID);
        return ratings.getUserRatings().stream()
            .map(rating -> movieInfo.getCatalogueItem(rating))
            .collect(Collectors.toList());
    }

    /**
     * Simple hardcoded fallback method for if getCatalogue fails.
     *
     * @param userID
     * @return List<CatalogueItem>
     */
    public List<CatalogueItem> getFallbackCatalogue(@PathVariable("userID") String userID) {
        return Arrays.asList(new CatalogueItem("No movies available", "", 0));
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