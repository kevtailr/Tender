package com.techelevator.Service;

import com.techelevator.Service.DTO.BusinessesDTO;
import com.techelevator.Service.DTO.ExtModels.ApiCategories;
import com.techelevator.Service.DTO.RestaurantDTO;
import com.techelevator.model.Restaurant;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Component
public class RestaurantService {

    private final String BASE_URL = "https://api.yelp.com/v3";
    private final String API_KEY = "[REDACTED]";
    private RestTemplate restTemplate = new RestTemplate();

    public RestaurantService() {
    }


    //TODO: figure out why the location isn't being passed through
    public List<RestaurantDTO> getAllRestaurants(String location) {

        List<RestaurantDTO> searchResults = new ArrayList<>();

        //the "open_now" search param will default to false. how can we use Yelp to help us toggle open on the front end?
        String restaurantByLocationEndpoint = String.format("/businesses/search?location=%s&categories=restaurants&limit=48&offset=48", location);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + API_KEY);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<BusinessesDTO> response = new RestTemplate().exchange(BASE_URL + restaurantByLocationEndpoint, HttpMethod.GET, request, BusinessesDTO.class);

        //TO TEST FUNCTIONALITY
        for (RestaurantDTO rest : response.getBody().getBusinesses()) {
            searchResults.add(rest);
            System.out.println(rest.toString());
        }

        return searchResults;
    }


    public RestaurantDTO getRestaurantByYelpKey(String yelpKey) {

        String restaurantByLocationEndpoint = String.format("/businesses/%s", yelpKey);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + API_KEY);
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<RestaurantDTO> response = new RestTemplate().exchange(BASE_URL + restaurantByLocationEndpoint, HttpMethod.GET, request, RestaurantDTO.class);

        System.out.println(response.getBody().getName());

        RestaurantDTO likedRestaurants = response.getBody();

        return likedRestaurants;

    }

}






