package com.example.popularmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.example.popularmovies.databinding.ActivityMainBinding
import com.example.popularmovies.interfaces.ApiService
import com.example.popularmovies.models.ListMovies
import com.example.popularmovies.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private val movie1:Movie=Movie("https://upload.wikimedia.org/wikipedia/commons/thumb/e/ea/Logo_Grupo_Imagen_Multimedia.2016.png/245px-Logo_Grupo_Imagen_Multimedia.2016.png",
        true,"","",
        arrayOfNulls<Int>(0),1,"",
        "","HOLAAAA",null,4.0,2,true,
    0.0,3,3)

    //private var movieList:ListMovies=ListMovies(0, arrayOf<Movie?>(movie1),0,0)
    private lateinit var movieList:ListMovies
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchByName("")

    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getMovies("movie/popular?api_key=9c79e7d3bea364cfc8315fc3c29f01a9")
            val puppies = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    //show recyclerview
                    val images = puppies?:ListMovies(0, arrayOfNulls<Movie>(0),0,0)
                    movieList=images
                    movieList.results.forEach { movie-> movie?.poster_path="https://image.tmdb.org/t/p/w500"+movie?.poster_path }
                    println(movieList.results[1]?.poster_path)
                    println(movieList.results.size)
                    println(movieList.results[1])
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






}