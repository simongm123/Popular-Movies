package com.example.popularmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popularmovies.databinding.MovieDetailsBinding
import com.example.popularmovies.models.Movie
import com.squareup.picasso.Picasso


class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: MovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val bundle: Bundle? = intent.extras
        val movie=bundle?.getSerializable("movie") as Movie
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+movie.poster_path).into(binding.ivMovie)
        binding.apply {
            tvTitle.text=movie.title.toString()
            tvGender.text=movie.genre_ids[0].toString()
            tvOriginalLanguage.text=movie.original_language.toString()
            tvPopularity.text=movie.popularity.toString()
            tvReleaseDate.text=movie.release_date.toString()
            tvVoteCount.text=movie.vote_count.toString()
            tvVoteAverage.text=movie.vote_average.toString()
            tvTotalResult.text=movie.total_results.toString()
            tvOverview.text=movie.overview.toString()
        }


    }
}