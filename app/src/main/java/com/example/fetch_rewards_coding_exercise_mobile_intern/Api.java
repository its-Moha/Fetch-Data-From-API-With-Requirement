package com.example.fetch_rewards_coding_exercise_mobile_intern;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Api {

    @GET("hiring.json")
    Call<List<MainData>> getData();

}
