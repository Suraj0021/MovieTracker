package com.suraj.movietracker.module

data class MovieResponse(
    val dates: Dates,
    val page: Int,
    val results: List<MovieData>,
    val total_pages: Int,
    val total_results: Int
)