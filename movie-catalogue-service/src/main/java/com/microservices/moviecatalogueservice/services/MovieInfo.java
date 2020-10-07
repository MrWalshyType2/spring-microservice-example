package com.microservices.moviecatalogueservice.services;

import com.microservices.moviecatalogueservice.models.CatalogueItem;
import com.microservices.moviecatalogueservice.models.Movie;
import com.microservices.moviecatalogueservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogueItem")
    public CatalogueItem getCatalogueItem(Rating rating) {
        // For each movie ID, call the movie info API and get the details
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieID(), Movie.class);
        // Then put the rating and movie together
        return new CatalogueItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogueItem getFallbackCatalogueItem(Rating rating) {
        return new CatalogueItem("Movie not found", "Movie not found", rating.getRating());
    }
}
