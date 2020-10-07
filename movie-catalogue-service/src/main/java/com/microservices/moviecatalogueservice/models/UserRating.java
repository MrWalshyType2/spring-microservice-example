package com.microservices.moviecatalogueservice.models;

import java.util.List;

/**
 * Wrapper class for Rating.
 */
public class UserRating {

    private String userID;
    private List<Rating> userRatings;

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
