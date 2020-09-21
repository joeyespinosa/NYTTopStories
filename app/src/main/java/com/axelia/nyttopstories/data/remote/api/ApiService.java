package com.axelia.nyttopstories.data.remote.api;

import com.axelia.nyttopstories.data.model.TopStoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("svc/topstories/v2/{type}.json")
    Call<TopStoryResponse> getTopStories(
            @Path("type") String type,
            @Query("api-key") String apiKey);
}
