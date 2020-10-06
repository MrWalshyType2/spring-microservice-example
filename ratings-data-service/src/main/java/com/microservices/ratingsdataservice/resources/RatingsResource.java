package com.microservices.ratingsdataservice.resources;

import com.microservices.ratingsdataservice.models.Rating;
import com.microservices.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

    @RequestMapping("/{movieID}")
    public Rating getRating(@PathVariable("movieID") String movieID) {
        return new Rating(movieID, 5);
    }

    @RequestMapping("users/{userID}")
    public UserRating getUserRatings(@PathVariable("userID") String userID) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1", 4),
                new Rating("2", 2)
        );
        UserRating userRatings = new UserRating();
        userRatings.setUserRatings(ratings);
        return userRatings;
    }
}
