package com.example.sampleapplication2.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication2.R
import com.example.sampleapplication2.models.Result
import com.example.sampleapplication2.ui.detail.MovieDetailActivity
import com.example.sampleapplication2.util.Constants
import com.google.gson.Gson

class PopularMoviesAdapter(
    activity: AppCompatActivity,
    movieList: List<Result>
) : RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder?>() {
    private val mItems: List<Result> = movieList
    private val activity: AppCompatActivity = activity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Result = mItems[position]
        holder.tvName.text = movie.title
        holder.tvDesc.text = movie.popularity.toString()
        Glide.with(activity).load(movie.posterPath).diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate().fitCenter().placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.ivMovie)
        holder.tvCardView.setOnClickListener {
            val prefs = activity.getSharedPreferences(Constants.APP_NAME, Context.MODE_PRIVATE)
            val gSon = Gson()
            val json = gSon.toJson(movie)
            prefs!!.edit().putString(Constants.MOVIE_DETAIL, json).apply()
            val intent = Intent(activity, MovieDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var ivMovie: AppCompatImageView = itemView.findViewById(R.id.iv_movie)
        var tvName: TextView = itemView.findViewById(R.id.tv_name)
        var tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        var tvCardView: CardView = itemView.findViewById(R.id.tv_card_view)

    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}
