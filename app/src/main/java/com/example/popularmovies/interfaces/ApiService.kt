package com.example.popularmovies.interfaces

import com.example.popularmovies.models.ListMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getMovies(@Url url:String): Response<ListMovies>
}
