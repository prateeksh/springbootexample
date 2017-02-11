package com.example.prateek.springbootexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Prateek on 03-02-2017.
 */

public interface UserService {

    @GET("/result")
    Call<List<User>> all();

    @GET("/{name}")
    Call<User> get(@Path("name") String name);

    @POST("/addnew")
    Call<User> update(@Body User user);
}
