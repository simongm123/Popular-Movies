package com.example.popularmovies.models

import java.io.Serializable

data class Movie(var poster_path:String?=null,
                 var adult:Boolean,
                 var overview:String,
                 var release_date:String,
                 var genre_ids: Array<Int?>,
                 var id:Int,
                 var original_title:String,
                 var original_language:String,
                 var title:String,
                 var backdrop_path:String?=null,
                 var popularity:Double,
                 var vote_count:Int,
                 var video:Boolean,
                 var vote_average:Double,
                 var total_results:Int,
                 var total_pages:Int
                ): Serializable




