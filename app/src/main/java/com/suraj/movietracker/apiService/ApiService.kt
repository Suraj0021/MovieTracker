package com.suraj.movietracker.apiService

import com.suraj.movietracker.module.MovieResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDBiZDUyYmNhMTZhZjNiMDQ4ODhkNjQwMjE3ZjMyOCIsInN1YiI6IjY0OWJlYjJhOTYzODY0MDEzYTMyYmFhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XcP_gYVsPMdWNIG4lLdwDxWTkSDRYU6G6JH1IrIgKeI"
    )
    @GET("movie/now_playing?language=en-US&")
    suspend fun getNowPlayingMoviesData(@Query("page") page: Int): Response<MovieResponse>?

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDBiZDUyYmNhMTZhZjNiMDQ4ODhkNjQwMjE3ZjMyOCIsInN1YiI6IjY0OWJlYjJhOTYzODY0MDEzYTMyYmFhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XcP_gYVsPMdWNIG4lLdwDxWTkSDRYU6G6JH1IrIgKeI"
    )
    @GET("movie/popular?language=en-US&")
    suspend fun getPopulerMoviesData(@Query("page") page: Int): Response<MovieResponse>?

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDBiZDUyYmNhMTZhZjNiMDQ4ODhkNjQwMjE3ZjMyOCIsInN1YiI6IjY0OWJlYjJhOTYzODY0MDEzYTMyYmFhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XcP_gYVsPMdWNIG4lLdwDxWTkSDRYU6G6JH1IrIgKeI"
    )
    @GET("movie/top_rated?language=en-US&")
    suspend fun getTopRatedMoviesData(@Query("page") page: Int): Response<MovieResponse>

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDBiZDUyYmNhMTZhZjNiMDQ4ODhkNjQwMjE3ZjMyOCIsInN1YiI6IjY0OWJlYjJhOTYzODY0MDEzYTMyYmFhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XcP_gYVsPMdWNIG4lLdwDxWTkSDRYU6G6JH1IrIgKeI"
    )
    @GET("movie/upcoming?language=en-US&")
    suspend fun getUpcomingMoviesData(@Query("page") page: Int): Response<MovieResponse>?


    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDBiZDUyYmNhMTZhZjNiMDQ4ODhkNjQwMjE3ZjMyOCIsInN1YiI6IjY0OWJlYjJhOTYzODY0MDEzYTMyYmFhYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.XcP_gYVsPMdWNIG4lLdwDxWTkSDRYU6G6JH1IrIgKeI"
    )
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page :Int
    ): Response<MovieResponse>?

}
