package com.example.popularmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.popularmovies.databinding.MovieDetailsBinding
import com.example.popularmovies.models.Movie


class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: MovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle: Bundle? = intent.extras
        val report=bundle?.getSerializable("movie") as Movie





    }
}