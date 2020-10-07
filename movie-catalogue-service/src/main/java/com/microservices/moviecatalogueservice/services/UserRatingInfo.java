package com.microservices.moviecatalogueservice.services;

import com.microservices.moviecatalogueservice.models.Rating;
import com.microservices.moviecatalogueservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userID) {
        return restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userID, UserRating.class);
    }

    public UserRating getFallbackUserRating(String userID) {
        UserRating userRating = new UserRating();
        userRating.setUserID(userID);
        userRating.setUserRatings(Arrays.asList(
                new Rating("0", 0)
        ));
        return userRating;
    }
}
