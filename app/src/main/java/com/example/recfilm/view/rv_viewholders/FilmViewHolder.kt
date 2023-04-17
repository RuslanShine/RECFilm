package com.example.recfilm.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remote_module.entity.ApiConstants
import com.example.recfilm.databinding.FilmItemBinding
import com.example.recfilm.data.Entity.Film


class FilmViewHolder(val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val title = binding.title
    private val poster = binding.poster
    private val description = binding.description
    private val ratingDonut = binding.ratingDonut

    fun bind(film: Film) {
        title.text = film.title
        Glide.with(binding.root)
            .load(com.example.remote_module.entity.ApiConstants.IMAGES_URL + "w400" + film.poster)
            .centerCrop()
            .into(poster)
        description.text = film.description
        ratingDonut.setProgress((film.rating * 10).toInt())
    }
}