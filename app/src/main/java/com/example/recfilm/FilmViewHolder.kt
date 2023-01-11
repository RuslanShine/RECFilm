package com.example.recfilm

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recfilm.databinding.FilmItemBinding


class FilmViewHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title = binding.title
    private val poster = binding.poster
    private val description = binding.description
    private val ratingDonut = binding.ratingDonut

    fun bind(film: Film) {
        title.text = film.title
        Glide.with(binding.root)
            .load(film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}