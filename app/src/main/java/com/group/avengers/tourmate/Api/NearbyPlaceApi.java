package com.group.avengers.tourmate.Api;

import com.group.avengers.tourmate.NearByPlaces.NearbyByPlaceContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NearbyPlaceApi {

    @GET()
    Call<NearbyByPlaceContent> getNearbyPlace(@Url String url);
}
