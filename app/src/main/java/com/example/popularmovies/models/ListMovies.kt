package com.example.popularmovies.models

data class ListMovies(
    var page:Int,
    var results: Array<Movie?>,
    var total_results:Int,
    var total_pages:Int)
