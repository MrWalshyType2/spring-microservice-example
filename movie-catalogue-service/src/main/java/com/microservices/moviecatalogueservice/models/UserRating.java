package com.microservices.moviecatalogueservice.models;

import java.util.List;

/**
 * Wrapper class for Rating.
 */
public class UserRating {

    private List<Rating> userRatings;

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }
}
