package com.example.popularmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularmovies.databinding.ActivityMainBinding
import com.example.popularmovies.interfaces.ApiService
import com.example.popularmovies.models.ListMovies
import com.example.popularmovies.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var movieList:ListMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.svMovies.setOnQueryTextListener(this)
        watchPopularMovies()

    }

    // Send the base url and receive the json object thought serialization
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun watchPopularMovies(){
        //Coroutine to make actions in a second thread
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getMovies("movie/popular?api_key=9c79e7d3bea364cfc8315fc3c29f01a9")
            val movies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    //show recyclerview
                    val images = movies?:ListMovies(0, arrayOfNulls<Movie>(0),0,0)
                    movieList=images
                    movieList.results.forEach { movie-> movie?.poster_path="https://image.tmdb.org/t/p/w500"+movie?.poster_path }
                    initRecyclerView()
                    adapter.notifyDataSetChanged()
                } else {
                    //show error
                    showError()
                }
            }
        }
    }
    private fun searchByName(query:String){
        //Coroutine to make actions in a second thread
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getMovies("/search/multi?api_key=9c79e7d3bea364cfc8315fc3c29f01a9&query=$query")
            val movies = call.body()
            println(movies.toString())
            runOnUiThread {
                if (call.isSuccessful) {
                    //show recyclerview
                    val images = movies?:ListMovies(0, arrayOfNulls<Movie>(0),0,0)
                    movieList=images
                    movieList.results.forEach { movie-> movie?.poster_path="https://image.tmdb.org/t/p/w500"+movie?.poster_path }
                    initRecyclerView()
                    adapter.notifyDataSetChanged()
                } else {
                    //show error
                    showError()
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(movieList) { onItemSelected(it) }
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        binding.rvMovies.adapter = adapter
    }

    private fun onItemSelected(movie:Movie){
        Toast.makeText(this, "${movie.toString()}", Toast.LENGTH_SHORT).show()
        val intent= Intent(this, DetailsMovieActivity::class.java).apply {
            putExtra("movie",movie)
        }
        startActivity(intent)
    }
    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
           searchByName(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


}