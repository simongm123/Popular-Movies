package com.example.popularmovies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies.databinding.OneItemMovieBinding
import com.example.popularmovies.models.ListMovies
import com.example.popularmovies.models.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val listOfMovies:ListMovies,private val onClicListener:(Movie)->Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = OneItemMovieBinding.bind(view)
        val textView: TextView

        // Function to Convert Url in Image and to give clic listener
        fun bind(movie:Movie,image:String,onClicListener:(Movie)->Unit){
            Picasso.get().load(image).into(binding.imageViewMovie)
            itemView.setOnClickListener { onClicListener(movie) }
        }
        init {
            textView = view.findViewById(R.id.tv_title_movie)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.one_item_movie, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val item=listOfMovies.results[position]?.poster_path
        listOfMovies.results[position]?.let { viewHolder.bind(it,item?:"",onClicListener) }
        viewHolder.textView.text=listOfMovies.results[position]?.title.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listOfMovies.results.size



}