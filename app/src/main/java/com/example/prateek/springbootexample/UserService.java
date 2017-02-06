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

    @GET("users")
    Call<List<User>> all();

    @GET("users/{name}")
    Call<User> get(@Path("name") String name);

    @POST("users/new")
    Call<User> create(@Body User user);
}
